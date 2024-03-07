package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;
import com.leis.hxds.bff.customer.controller.form.*;
import com.leis.hxds.bff.customer.feign.*;
import com.leis.hxds.bff.customer.service.OrderService;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.R;
import com.leis.hxds.common.wxpay.MyWXPayConfig;
import com.leis.hxds.common.wxpay.WXPay;
import com.leis.hxds.common.wxpay.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private MyWXPayConfig myWXPayConfig;
    @Resource
    private VhrServiceApi vhrServiceApi;
    @Resource
    private CstServiceApi cstServiceApi;
    @Resource
    private DrServiceApi drServiceApi;
    @Resource
    private MpsServiceApi mpsServiceApi;
    @Resource
    private RuleServiceApi ruleServiceApi;
    @Resource
    private OdrServiceApi odrServiceApi;
    @Resource
    private SnmServiceApi snmServiceApi;

    @Override
    @Transactional
    @LcnTransaction
    public HashMap createNewOrder(CreateNewOrderForm form) {
        Long customerId = form.getCustomerId();
        String startPlace = form.getStartPlace();
        String startPlaceLatitude = form.getStartPlaceLatitude();
        String startPlaceLongitude = form.getStartPlaceLongitude();
        String endPlace = form.getEndPlace();
        String endPlaceLatitude = form.getEndPlaceLatitude();
        String endPlaceLongitude = form.getEndPlaceLongitude();
        String favourFee = form.getFavourFee();

        /**
         * 【重新预估里程和时间】
         * 虽然下单前，系统会预估里程和时长，但是有可能顾客在下单页面停留时间过长，然后再按下单键，这时候路线和时长可能都有变化，所以需要重新预估里程和时间案
         */
        EstimateOrderMileageAndMinuteForm form1 = new EstimateOrderMileageAndMinuteForm();
        form1.setMode("driving");
        form1.setStartPlaceLatitude(startPlaceLatitude);
        form1.setStartPlaceLongitude(startPlaceLongitude);
        form1.setEndPlaceLatitude(endPlaceLatitude);
        form1.setEndPlaceLongitude(endPlaceLongitude);
        R r = mpsServiceApi.estimateOrderMileageAndMinute(form1);
        HashMap map = (HashMap) r.get("result");
        String mileage = MapUtil.getStr(map, "mileage");
        int minute = MapUtil.getInt(map, "minute");

        /**
         * 重新估算订单金额
         */
        EstimateOrderChargeForm form2 = new EstimateOrderChargeForm();
        form2.setMileage(mileage);
        form2.setTime(new DateTime().toTimeStr());
        r = ruleServiceApi.estimateOrderCharge(form2);
        map = (HashMap) r.get("result");
        String expectsFee = MapUtil.getStr(map, "amount");
        String chargeRuled = MapUtil.getStr(map, "chargeRuled");
        short baseMileage = MapUtil.getShort(map, "baseMileage");
        String baseMileagePrice = MapUtil.getStr(map, "baseMileagePrice");
        String exceedMileagePrice = MapUtil.getStr(map, "exceedMileagePrice");
        short baseMinute = MapUtil.getShort(map, "baseMinute");
        String exceedMinutePrice = MapUtil.getStr(map, "exceedMinutePrice");
        Short baseReturnMileage = MapUtil.getShort(map, "baseReturnMileage");
        String exceedReturnPrice = MapUtil.getStr(map, "exceedReturnPrice");
        /**
         * 搜索适合接单的司机
         */
        SearchBefittingDriverAboutOrderForm form3 = new SearchBefittingDriverAboutOrderForm();
        form3.setStartPlaceLatitude(startPlaceLatitude);
        form3.setStartPlaceLongitude(startPlaceLongitude);
        form3.setEndPlaceLatitude(endPlaceLatitude);
        form3.setEndPlaceLongitude(endPlaceLongitude);
        form3.setMileage(mileage);
        r = mpsServiceApi.searchBefittingDriverAboutOrder(form3);
        ArrayList<HashMap> list = (ArrayList<HashMap>) r.get("result");
        HashMap result = new HashMap() {{
            put("count", 0);
        }};

        if (list.size() > 0) {
            /**
             * 生成订单记录
             */
            InsertOrderForm form4 = new InsertOrderForm();
            form4.setUuid(IdUtil.simpleUUID());
            form4.setCustomerId(customerId);
            form4.setStartPlace(startPlace);
            form4.setStartPlaceLatitude(startPlaceLatitude);
            form4.setStartPlaceLongitude(startPlaceLongitude);
            form4.setEndPlace(endPlace);
            form4.setEndPlaceLatitude(endPlaceLatitude);
            form4.setEndPlaceLongitude(endPlaceLongitude);
            form4.setExpectsMileage(mileage);
            form4.setExpectsFee(expectsFee);
            form4.setDate(new DateTime().toTimeStr());
            form4.setChargeRuleId(Long.parseLong(chargeRuled));
            form4.setCarPlate(form.getCarPlate());
            form4.setCarType(form.getCarType());
            form4.setBaseMileage(baseMileage);
            form4.setBaseMileagePrice(baseMileagePrice);
            form4.setExceedMileagePrice(exceedMileagePrice);
            form4.setBaseMinute(baseMinute);
            form4.setExceedMinutePrice(exceedMinutePrice);
            form4.setBaseReturnMileage(baseReturnMileage);
            form4.setExceedReturnPrice(exceedReturnPrice);

            r = odrServiceApi.insertOrder(form4);
            String orderId = MapUtil.getStr(r, "result");
            //todo 发送通知给符合条件的司机抢单

            SendNewOrderMessageForm form5 = new SendNewOrderMessageForm();
            String[] driverContent = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                HashMap one = list.get(i);
                String driverId = MapUtil.getStr(one, "driverId");
                String distance = MapUtil.getStr(one, "distance");
                distance = new BigDecimal(distance).setScale(1, RoundingMode.CEILING).toString();
                driverContent[i] = driverId + "#" + distance;
            }
            form5.setDriversContent(driverContent);
            form5.setOrderId(Long.parseLong(orderId));
            form5.setFrom(startPlace);
            form5.setTo(endPlace);
            form5.setExpectsFee(expectsFee);

            mileage = new BigDecimal(mileage).setScale(1, RoundingMode.CEILING).toString();
            form5.setMileage(mileage);
            form5.setMinute(minute);
            form5.setFavourFee(favourFee);
            snmServiceApi.sendNewOrderMessageAsync(form5);

            result.put("orderId", orderId);
            result.replace("count", orderId);
        }
        return result;
    }

    @Override
    public Integer searchOrderStatus(SearchOrderStatusForm form) {
        R r = odrServiceApi.searchOrderStatus(form);
        Integer status = MapUtil.getInt(r, "result");
        return status;
    }

    @Override
    @Transactional
    @LcnTransaction
    public String deleteUnAcceptOrder(DeleteUnAcceptOrderForm form) {
        R r = odrServiceApi.deleteUnAcceptOrder(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }

    @Override
    public HashMap hasCustomerCurrentOrder(HasCustomerCurrentOrderForm form) {
        R r = odrServiceApi.hasCustomerCurrentOrder(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }

    @Override
    public boolean confirmArriveStartPlace(ConfirmArriveStartPlaceForm form) {
        R r = odrServiceApi.confirmArriveStartPlace(form);
        boolean result = MapUtil.getBool(r, "result");
        return result;
    }

    @Override
    public HashMap searchOrderById(SearchOrderByIdForm form) {
        R r = odrServiceApi.searchOrderById(form);
        HashMap map = (HashMap) r.get("result");
        Long driverId = MapUtil.getLong(map, "driverId");
        if (driverId != null) {
            SearchDriverBriefInfoForm infoForm = new SearchDriverBriefInfoForm();
            infoForm.setDriverId(driverId);
            r = drServiceApi.searchDriverBriefInfo(infoForm);
            HashMap temp = (HashMap) r.get("result");
            map.putAll(temp);
            return map;
        }
        return null;
    }

    @Override
    @Transactional
    @LcnTransaction
    public HashMap createWxPayment(long orderId, long customerId, Long voucherId) {
        /**
         * 1.先查询订单是否为6状态，其他状态都不可以生成支付订单
         */
        ValidCanPayOrderForm form1 = new ValidCanPayOrderForm();
        form1.setOrderId(orderId);
        form1.setCustomerId(customerId);
        R r = odrServiceApi.validCanPayOrder(form1);
        HashMap map = (HashMap) r.get("result");
        String amount = MapUtil.getStr(map, "realFee");
        String uuid = MapUtil.getStr(map, "uuid");
        Long driverId = MapUtil.getLong(map, "driverId");
        String discount = "0.00";
        if (voucherId != null) {
            /**
             * 2.查询代金券是否可以使用，并绑定
             */
            UseVoucherForm form2 = new UseVoucherForm();
            form2.setCustomerId(customerId);
            form2.setVoucherId(voucherId);
            form2.setOrderId(orderId);
            form2.setAmount(amount);
            r = vhrServiceApi.userVoucher(form2);
            discount = MapUtil.getStr(r, "result");
        }
        if (new BigDecimal(amount).compareTo(new BigDecimal(discount)) == -1) {
            throw new HxdsException("总金额不能小于优惠券面额");
        }
        /**
         * 3.修改实付金额
         */
        amount = NumberUtil.sub(amount, discount).toString();
        UpdateBillPaymentForm form3 = new UpdateBillPaymentForm();
        form3.setOrderId(orderId);
        form3.setRealPay(amount);
        form3.setVoucherFee(discount);
        odrServiceApi.updateBillPayment(form3);

        /**
         * 4.查询用户的openID字符串
         */
        SearchCustomerOpenIdForm form4 = new SearchCustomerOpenIdForm();
        form4.setCustomerId(customerId);
        r = cstServiceApi.searchCustomerOpenId(form4);
        String customerOpenId = MapUtil.getStr(r, "result");
        /**
         * 5.查询司机的openId字符串
         */
        SearchDriverOpenIdForm form5 = new SearchDriverOpenIdForm();
        form5.setDriverId(driverId);
        r = drServiceApi.searchDriverOpenId(form5);
        String driverOpenId = MapUtil.getStr(r, "result");
        /**
         * 6.创建支付订单
         */
        try {
            WXPay wxPay = new WXPay(myWXPayConfig);
            HashMap param = new HashMap();
            param.put("nonce_str", WXPayUtil.generateNonceStr()); // 随机字符串
            param.put("body", "代驾费");
            param.put("out_trade_no", uuid);
            //充值金额转换成分为单位，并且让BigDecimal取整数
            param.put("total_fee", NumberUtil.mul(amount, "100").setScale(0, RoundingMode.FLOOR)).toString();
            param.put("spbill_create_ip", "127.0.0.1");
            //todo 这里要修改成内网穿透的公网URL
            param.put("notify_url", "http://demo.com");
            param.put("trade_type", "JSAPI");
            param.put("openId", customerOpenId);
            param.put("attach", driverOpenId);
            param.put("profit_sharing", "Y"); //支付需要分账

            //创建支付订单
            Map<String, String> result = wxPay.unifiedOrder(param);

            //预支付交易会话标识ID
            String prepayId = result.get("prepay_id");
            if (prepayId != null) {
                /**
                 * 7.更新订单记录中的prepay_id字段值
                 */
                UpdateOrderPrepayIdForm form6 = new UpdateOrderPrepayIdForm();
                form6.setOrderId(orderId);
                form6.setPrepayId(prepayId);
                odrServiceApi.updateOrderPrepayId(form6);

                //准备生成数字签名用的数据
                map.clear();
                map.put("appId", myWXPayConfig.getAppID());
                String timeStamp = new Date().getTime() + "";
                map.put("timeStamp", timeStamp);
                String nonceStr = WXPayUtil.generateNonceStr();
                map.put("nonceStr", nonceStr);
                map.put("package", "prepay_id" + prepayId);
                map.put("signType", "MD5");

                //生成数据签名
                String signature = WXPayUtil.generateSignature(map, myWXPayConfig.getKey());

                map.clear();
                map.put("package", "prepay_id" + prepayId);
                map.put("timeStamp", timeStamp);
                map.put("nonceStr", nonceStr);
                map.put("paySign", signature);
                //uuid用于付款成功后，移动端主动请求更新充值状态
                map.put("uuid", uuid);
                return map;
            }else{
                log.error("创建支付订单失败");
                throw new HxdsException("创建支付订单失败");
            }
        } catch (Exception e) {
            log.error("创建支付订单失败");
            throw new HxdsException("创建支付订单失败");
        }
    }
}
