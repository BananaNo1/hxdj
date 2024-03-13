package com.leis.hxds.bff.driver.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.bff.driver.controller.form.*;
import com.leis.hxds.bff.driver.feign.*;
import com.leis.hxds.bff.driver.service.OrderService;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.RoundingMode;
import java.util.HashMap;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private SnmServiceApi snmServiceApi;
    @Resource
    private RuleServiceApi ruleServiceApi;
    @Resource
    private OdrServiceApi odrServiceApi;
    @Resource
    private CstServiceApi cstServiceApi;
    @Resource
    private NebulaServiceApi nebulaServiceApi;

    @Override
    public String acceptNewOrder(AcceptNewOrderForm form) {
        R r = odrServiceApi.acceptNewOrder(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }

    @Override
    public HashMap searchDriverExecuteOrder(SearchDriverExecuteOrderForm form) {
        //查询订单信息
        R r = odrServiceApi.searchDriverExecuteOrder(form);
        HashMap orderMap = (HashMap) r.get("result");

        //查询代驾客户信息
        long customerId = MapUtil.getLong(orderMap, "customerId");
        SearchCustomerInfoInOrderForm infoInOrderForm = new SearchCustomerInfoInOrderForm();
        infoInOrderForm.setCustomerId(customerId);
        r = cstServiceApi.searchCustomerInfoInOrder(infoInOrderForm);
        HashMap cstMap = (HashMap) r.get("result");

        HashMap map = new HashMap();
        map.putAll(orderMap);
        map.putAll(cstMap);
        return map;
    }

    @Override
    public HashMap searchDriverCurrentOrder(SearchDriverCurrentOrderForm form) {
        R r = odrServiceApi.searchDriverCurrentOrder(form);
        HashMap orderMap = (HashMap) r.get("result");
        if (MapUtil.isNotEmpty(orderMap)) {
            HashMap map = new HashMap();
            //查询代驾客户信息
            long customerId = MapUtil.getLong(orderMap, "customerId");
            SearchCustomerInfoInOrderForm infoInOrderForm = new SearchCustomerInfoInOrderForm();
            infoInOrderForm.setCustomerId(customerId);
            r = cstServiceApi.searchCustomerInfoInOrder(infoInOrderForm);
            HashMap cstMap = (HashMap) r.get("result");
            map.putAll(orderMap);
            map.putAll(cstMap);
            return map;
        } else {
            return null;
        }
    }

    @Override
    public HashMap searchOrderForMoveById(SearchOrderForMoveByIdForm form) {
        R r = odrServiceApi.searchOrderForMoveById(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }

    @Override
    @LcnTransaction
    @Transactional
    public int arriveStartPlace(ArriveStartPlaceForm form) {
        R r = odrServiceApi.arriveStartPlace(form);
        int rows = MapUtil.getInt(r, "rows");
        if (rows == 1) {
            //todo 发送通知消息
        }
        return rows;
    }

    @Override
    public int startDriving(StartDrivingForm form) {
        R r = odrServiceApi.startDriving(form);
        int rows = MapUtil.getInt(r, "rows");
        if (rows == 1) {
            InsertOrderMonitoringForm monitoringForm = new InsertOrderMonitoringForm();
            monitoringForm.setOrderId(form.getOrderId());
            nebulaServiceApi.insertOrderMonitoring(monitoringForm);
            //todo 发送消息通知
        }
        return rows;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int updateOrderStatus(UpdateOrderStatusForm form) {
        R r = odrServiceApi.updateOrderStatus(form);
        int rows = MapUtil.getInt(r, "rows");
        //todo 判断订单的状态，然后实现后续业务
        if (rows != 1) {
            throw new HxdsException("订单状态修改失败");
        }
        if (form.getStatus() == 0) {
            SendPrivateMessageForm messageForm = new SendPrivateMessageForm();
            messageForm.setReceiverIdentity("customer_bill");
            messageForm.setReceiverId(form.getCustomerId());
            messageForm.setTtl(3 * 24 * 3600 * 1000);
            messageForm.setSenderId(0L);
            messageForm.setSenderIdentity("system");
            messageForm.setSenderName("华夏代驾");
            messageForm.setMsg("您有代驾订单待支付");
            snmServiceApi.sendPrivateMessageAsync(messageForm);
        }
        return rows;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int updateOrderBill(UpdateBillFeeForm form) {
        /**
         * 1.判断司机是否关联订单
         */
        ValidDriverOwnOrderForm form1 = new ValidDriverOwnOrderForm();
        form1.setOrderId(form.getOrderId());
        form1.setDriverId(form.getDriverId());
        R r = odrServiceApi.validDriverOwnOrder(form1);
        boolean bool = MapUtil.getBool(r, "result");
        if (!bool) {
            throw new HxdsException("司机未关联该订单");
        }
        /**
         * 2.计算订单里程数据
         */
        CalculateOrderMileageForm form2 = new CalculateOrderMileageForm();
        form2.setOrderId(form.getOrderId());
        r = nebulaServiceApi.calculateOrderMileage(form2);
        String mileage = (String) r.get("result");
        mileage = NumberUtil.div(mileage, "1000", 1, RoundingMode.CEILING).toString();
        /**
         * 3.查询订单信息
         */
        SearchSettlementNeedDataForm form3 = new SearchSettlementNeedDataForm();
        form3.setOrderId(form.getOrderId());
        r = odrServiceApi.searchSettlementNeedData(form3);
        HashMap map = (HashMap) r.get("result");
        String acceptTime = MapUtil.getStr(map, "acceptTime");
        String startTime = MapUtil.getStr(map, "startTime");
        int waitingMinute = MapUtil.getInt(map, "waitingMinute");
        String favourFee = MapUtil.getStr(map, "favourFee");
        /**
         * 4.计算代价费
         */
        CalculateOrderChargeForm form4 = new CalculateOrderChargeForm();
        form4.setMileage(mileage);
        form4.setTime(startTime.split(" ")[1]);
        form4.setMinute(waitingMinute);
        r = ruleServiceApi.calculateOrderCharge(form4);
        map = (HashMap) r.get("result");
        String mileageFee = MapUtil.getStr(map, "mileageFee");
        String returnFee = MapUtil.getStr(map, "returnFee");
        String waitingFee = MapUtil.getStr(map, "waitingFee");
        String amount = MapUtil.getStr(map, "amount");
        String returnMileage = MapUtil.getStr(map, "returnMileage");

        /**
         * 5.计算系统奖励费用
         */
        CalculateIncentiveFeeForm form5 = new CalculateIncentiveFeeForm();
        form5.setAcceptTime(acceptTime);
        form5.setDriverId(form.getDriverId());
        r = ruleServiceApi.calculateIncentiveFee(form5);
        String incentiveFee = (String) r.get("result");

        form.setMileageFee(mileageFee);
        form.setReturnFee(returnFee);
        form.setWaitingFee(waitingFee);
        form.setIncentiveFee(incentiveFee);
        form.setRealMileage(mileage);
        form.setReturnMileage(returnMileage);
        //计算总费用
        String total =
                NumberUtil.add(amount, form.getTotalFee(), form.getParkingFee(), form.getOtherFee(), favourFee).toString();
        /**
         * 6.计算分账费用
         */
        CalculateProfitsharingForm form6 = new CalculateProfitsharingForm();
        form6.setOrderId(form.getOrderId());
        form6.setAmount(form.getTotal());
        r = ruleServiceApi.calculateProfitsharing(form6);
        map = (HashMap) r.get("result");
        long ruleId = MapUtil.getLong(map, "ruleId");
        String systemIncome = MapUtil.getStr(map, "systemIncome");
        String driverIncome = MapUtil.getStr(map, "driverIncome");
        String paymentRate = MapUtil.getStr(map, "paymentRate");
        String paymentFee = MapUtil.getStr(map, "paymentFee");
        String taxRate = MapUtil.getStr(map, "taxRate");
        String taxFee = MapUtil.getStr(map, "taxFee");
        form.setRuleId(ruleId);
        form.setPaymentRate(paymentRate);
        form.setPaymentFee(paymentFee);
        form.setTaxRate(taxRate);
        form.setTaxFee(taxFee);
        form.setSystemIncome(systemIncome);
        form.setDriverIncome(driverIncome);

        /**
         * 7.更新代驾费账单数据
         */
        r = odrServiceApi.updateBillFee(form);
        int rows = MapUtil.getInt(r, "rows");
        return rows;
    }

    @Override
    public HashMap searchReviewDriverOrderBill(SearchReviewDriverOrderBillForm form) {
        R r = odrServiceApi.searchReviewDriverOrderBill(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }

    @Override
    public Integer searchOrderStatus(SearchOrderStatusForm form) {
        R r = odrServiceApi.searchOrderStatus(form);
        Integer status = (Integer) r.get("result");
        return status;
    }

    @Override
    @Transactional
    @LcnTransaction
    public String updateOrderAboutPayment(long driverId, UpdateOrderAboutPaymentForm form) {
        ValidDriverOwnOrderForm validForm = new ValidDriverOwnOrderForm();
        validForm.setDriverId(driverId);
        validForm.setOrderId(form.getOrderId());
        R r = odrServiceApi.validDriverOwnOrder(validForm);
        boolean bool = MapUtil.getBool(r, "result");
        if (!bool) {
            throw new HxdsException("司机未关联该订单");
        }
        r = odrServiceApi.updateOrderAboutPayment(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }

    @Override
    public PageUtils searchDriverOrderByPage(SearchDriverOrderByPageForm form) {
        R r = odrServiceApi.searchDriverOrderByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }
}
