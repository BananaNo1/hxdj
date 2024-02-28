package com.leis.hxds.bff.driver.service.impl;

import com.leis.hxds.bff.driver.controller.form.RemoveLocationCacheForm;
import com.leis.hxds.bff.driver.controller.form.UpdateLocationCacheForm;
import com.leis.hxds.bff.driver.controller.form.UpdateOrderLocationCacheForm;
import com.leis.hxds.bff.driver.feign.MpsServiceApi;
import com.leis.hxds.bff.driver.service.DriverLocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class DriverLocationServiceImpl implements DriverLocationService {

    @Resource
    private MpsServiceApi mpsServiceApi;

    @Override
    public void updateLocationCache(UpdateLocationCacheForm form) {
        mpsServiceApi.updateLocationCache(form);
    }

    @Override
    public void removeLocationCache(RemoveLocationCacheForm form) {
        mpsServiceApi.removeLocationCache(form);
    }

    @Override
    public void updateOrderLocationCache(UpdateOrderLocationCacheForm form) {
        mpsServiceApi.updateOrderLocationCache(form);
    }
}
