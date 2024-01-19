package com.leis.hxds.bff.driver.service.impl;

import cn.hutool.core.convert.Convert;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.leis.hxds.bff.driver.controller.form.UpdateDriverAuthForm;
import com.leis.hxds.bff.driver.feign.DrServiceApi;
import com.leis.hxds.bff.driver.service.DriverService;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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
}
