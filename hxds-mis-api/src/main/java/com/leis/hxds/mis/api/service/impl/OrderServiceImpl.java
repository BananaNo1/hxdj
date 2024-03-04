package com.leis.hxds.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.*;
import com.leis.hxds.mis.api.feign.*;
import com.leis.hxds.mis.api.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private NebulaServiceApi nebulaServiceApi;
    @Resource
    private MpsServiceApi mpsServiceApi;
    @Resource
    private RuleServiceApi ruleServiceApi;
    @Resource
    private DrServiceApi drServiceApi;

    @Resource
    private CstServiceApi cstServiceApi;

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    public PageUtils searchOrderByPage(SearchOrderByPageForm form) {
        R r = odrServiceApi.searchOrderByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    public HashMap searchOrderComprehensiveInfo(long orderId) {
        HashMap map = new HashMap();

        //查询订单内容信息
        SearchOrderContentForm form1 = new SearchOrderContentForm();
        form1.setOrderId(orderId);
        R r = odrServiceApi.searchOrderContent(form1);
        if (!r.containsKey("result")) {
            throw new HxdsException("不存在订单记录");
        }

        HashMap content = (HashMap) r.get("result");
        map.put("content", content);

        //查询客户信息
        long customerId = MapUtil.getLong(content, "customerId");
        SearchCustomerBriefInfoForm form2 = new SearchCustomerBriefInfoForm();
        form2.setCustomerId(customerId);
        r = cstServiceApi.searchCustomerBriefInfo(form2);
        HashMap customerInfo = (HashMap) r.get("result");
        map.put("customerInfo", customerInfo);

        //查询司机信息
        long driverId = MapUtil.getLong(content, "driverId");
        SearchDriverBriefInfoForm form3 = new SearchDriverBriefInfoForm();
        form3.setDriverId(driverId);
        r = drServiceApi.searchDriverBriefInfo(form3);
        HashMap driverInfo = (HashMap) r.get("result");
        map.put("driverInfo", driverInfo);

        //查询消费规则
        if (content.containsKey("chargeRuleId")) {
            long chargeRuleId = MapUtil.getLong(content, "chargeRuleId");
            SearchChargeRuleByIdForm form4 = new SearchChargeRuleByIdForm();
            form4.setRuleId(chargeRuleId);
            r = ruleServiceApi.searchChargeRuleById(form4);
            HashMap chargeRule = (HashMap) r.get("result");
            map.put("chargeRule", chargeRule);
        }

        //查询取消规则
        if (content.containsKey("cancelRuleId")) {
            long cancelRuleId = MapUtil.getLong(content, "cancelRuleId");
            SearchCancelRuleByIdForm form5 = new SearchCancelRuleByIdForm();
            form5.setRuleId(cancelRuleId);
            r = ruleServiceApi.searchCancelRuleById(form5);
            HashMap cancelRule = (HashMap) r.get("result");
            map.put("cancelRule", cancelRule);
        }

        //查询分账规则
        if (content.containsKey("profitsharingRuleId")) {
            long profitsharingRuleId = MapUtil.getLong(content, "profitsharingRuleId");
            SearchProfitsharingRuleByIdForm form6 = new SearchProfitsharingRuleByIdForm();
            form6.setRuleId(profitsharingRuleId);
            r = ruleServiceApi.searchProfitsharingRuleById(form6);
            HashMap profitsharingRule = (HashMap) r.get("result");
            map.put("profitsharingRule", profitsharingRule);
        }

        //查询GPS规划线路
        CalculateDriveLineForm form7 = new CalculateDriveLineForm();
        HashMap startPlaceLocation = (HashMap) content.get("startPlaceLocation");
        HashMap endPlaceLocation = (HashMap) content.get("endPlaceLocation");
        form7.setStartPlaceLatitude(MapUtil.getStr(startPlaceLocation, "latitude"));
        form7.setStartPlaceLongitude(MapUtil.getStr(startPlaceLocation, "longitude"));
        form7.setEndPlaceLatitude(MapUtil.getStr(endPlaceLocation, "latitude"));
        form7.setEndPlaceLongitude(MapUtil.getStr(endPlaceLocation, "longitude"));
        r = mpsServiceApi.calculateDriverLine(form7);
        HashMap driverLine = (HashMap) r.get("result");
        map.put("driverLine", driverLine);

        int status = MapUtil.getInt(content, "status");
        if (status >= 5 && status <= 8) {
            //查询订单GPS定位记录
            SearchOrderGpsForm form8 = new SearchOrderGpsForm();
            r = nebulaServiceApi.searchOrderGps(form8);
            ArrayList<HashMap> orderGps = (ArrayList<HashMap>) r.get("result");
            map.put("orderGps", orderGps);
        } else if (status == 4) {
            //查询订单中最后的GPS定位
            SearchOrderLastGpsForm form9 = new SearchOrderLastGpsForm();
            form9.setOrderId(orderId);
            r = nebulaServiceApi.searchOrderLastGps(form9);
            HashMap lastGps = (HashMap) r.get("result");
            map.put("lastGps", lastGps);
        }
        return map;
    }

    @Override
    public HashMap searchOrderLastGps(SearchOrderLastGpsForm form) {
        SearchOrderStatusForm statusForm = new SearchOrderStatusForm();
        statusForm.setOrderId(form.getOrderId());
        R r = odrServiceApi.searchOrderStatus(statusForm);
        if (!r.containsKey("result")) {
            throw new HxdsException("没有对应的订单记录");
        }
        int status = MapUtil.getInt(r, "result");
        if (status == 4) {
            //查询订单最后的GPS记录
            r = nebulaServiceApi.searchOrderLastGps(form);
            HashMap lastGps = (HashMap) r.get("result");
            return lastGps;
        } else {
            return null;
        }
    }
}
