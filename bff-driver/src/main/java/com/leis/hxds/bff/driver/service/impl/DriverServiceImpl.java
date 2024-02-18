package com.leis.hxds.bff.driver.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.bff.driver.controller.form.*;
import com.leis.hxds.bff.driver.feign.DrServiceApi;
import com.leis.hxds.bff.driver.feign.OdrServiceApi;
import com.leis.hxds.bff.driver.service.DriverService;
import com.leis.hxds.common.util.CosUtil;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;

    @Resource
    private OdrServiceApi odrServiceApi;

    @Resource
    private CosUtil cosUtil;

    @Override
    @Transactional
    @LcnTransaction
    public long registerNewDriver(RegisterNewDriverForm form) {
        R r = drServiceApi.registerNewDriver(form);
        long userId = Convert.toLong(r.get("userId"));
        return userId;
    }

    @Override
    public int updateDriverAuth(UpdateDriverAuthForm form) {
        R r = drServiceApi.updateDriverAuth(form);
        int rows = Convert.toInt(r.get("rows"));
        return rows;
    }

    @Override
    @Transactional
    @LcnTransaction
    public String createDriverFaceModel(CreateDriverFaceModelForm form) {
        R r = drServiceApi.createDriverFaceModel(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }

    @Override
    public HashMap login(LoginForm form) {
        R r = drServiceApi.login(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }

    @Override
    public HashMap searchDriverBaseInfo(SearchDriverBaseInfoForm form) {
        R r = drServiceApi.searchDriverBaseInfo(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }

    @Override
    public HashMap searchWorkbenchData(long driverId) {
        SearchDriverTodayBusinessDataForm form1 = new SearchDriverTodayBusinessDataForm();
        form1.setDriverId(driverId);
        R r = odrServiceApi.searchDriverTodayBusinessData(form1);
        HashMap business = (HashMap) r.get("result");

        SearchDriverSettingForm form2 = new SearchDriverSettingForm();
        form2.setDriverId(driverId);
        r = drServiceApi.searchDriverSetting(form2);
        HashMap settings = (HashMap) r.get("result");
        HashMap result = new HashMap() {{
            put("business", business);
            put("settings", settings);
        }};
        return result;
    }

    @Override
    public HashMap searchDriverAuth(SearchDriverAuthForm form) {
        R r = drServiceApi.searchDriverAuth(form);
        HashMap map = (HashMap) r.get("result");
        String idcardFront = MapUtil.getStr(map, "idcardFront");
        String idcardBack = MapUtil.getStr(map, "idcardBack");
        String idcardHolding = MapUtil.getStr(map, "idcardHolding");
        String drcardFront = MapUtil.getStr(map, "drcardFront");
        String drcardBack = MapUtil.getStr(map, "drcardBack");
        String drcardHolding = MapUtil.getStr(map, "drcardHolding");
        String idcardFrontUrl = idcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(idcardFront) : "";
        String idcardBackUrl = idcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(idcardBack) : "";
        String idcardHoldingUrl = idcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(idcardHolding) : "";
        String drcardFrontUrl = drcardFront.length() > 0 ? cosUtil.getPrivateFileUrl(drcardFront) : "";
        String drcardBackUrl = drcardBack.length() > 0 ? cosUtil.getPrivateFileUrl(drcardBack) : "";
        String drcardHoldingUrl = drcardHolding.length() > 0 ? cosUtil.getPrivateFileUrl(drcardHolding) : "";
        map.put("idcardFrontUrl", idcardFrontUrl);
        map.put("idcardBackUrl", idcardBackUrl);
        map.put("idcardHoldingUrl", idcardHoldingUrl);
        map.put("drcardFrontUrl", drcardFrontUrl);
        map.put("drcardBackUrl", drcardBackUrl);
        map.put("drcardHoldingUrl", drcardHoldingUrl);
        return map;
    }
}
