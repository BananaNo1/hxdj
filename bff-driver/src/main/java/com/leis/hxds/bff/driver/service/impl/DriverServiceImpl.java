package com.leis.hxds.bff.driver.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.bff.driver.controller.form.*;
import com.leis.hxds.bff.driver.feign.DrServiceApi;
import com.leis.hxds.bff.driver.service.DriverService;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;

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
}
