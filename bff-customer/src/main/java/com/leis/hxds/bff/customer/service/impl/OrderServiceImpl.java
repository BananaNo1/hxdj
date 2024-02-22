package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;
import com.leis.hxds.bff.customer.controller.form.EstimateOrderChargeForm;
import com.leis.hxds.bff.customer.controller.form.EstimateOrderMileageAndMinuteForm;
import com.leis.hxds.bff.customer.controller.form.InsertOrderForm;
import com.leis.hxds.bff.customer.controller.form.SearchBefittingDriverAboutOrderForm;
import com.leis.hxds.bff.customer.feign.MpsServiceApi;
import com.leis.hxds.bff.customer.feign.OdrServiceApi;
import com.leis.hxds.bff.customer.feign.RuleServiceApi;
import com.leis.hxds.bff.customer.service.OrderService;
import com.leis.hxds.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Override
    @Transactional
    @LcnTransaction
    public int createNewOrder(CreateNewOrderForm form) {
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
        form4.setBaseMinute(baseMinute);
        form4.setExceedMileagePrice(exceedMinutePrice);
        form4.setBaseReturnMileage(baseReturnMileage);
        form4.setExceedReturnPrice(exceedReturnPrice);

        r = odrServiceApi.insertOrder(form4);
        String orderId = MapUtil.getStr(r, "result");
        //todo 发送通知给符合条件的司机抢单

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

        if(list.size()>0){

        }

        return 0;
    }
}
