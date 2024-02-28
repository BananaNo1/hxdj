package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;
import com.leis.hxds.bff.customer.controller.form.*;
import com.leis.hxds.bff.customer.feign.MpsServiceApi;
import com.leis.hxds.bff.customer.feign.OdrServiceApi;
import com.leis.hxds.bff.customer.feign.RuleServiceApi;
import com.leis.hxds.bff.customer.feign.SnmServiceApi;
import com.leis.hxds.bff.customer.service.OrderService;
import com.leis.hxds.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

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
}
