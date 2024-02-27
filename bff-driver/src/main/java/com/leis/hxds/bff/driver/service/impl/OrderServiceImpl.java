package com.leis.hxds.bff.driver.service.impl;

import cn.hutool.core.map.MapUtil;
import com.leis.hxds.bff.driver.controller.form.AcceptNewOrderForm;
import com.leis.hxds.bff.driver.feign.OdrServiceApi;
import com.leis.hxds.bff.driver.service.OrderService;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    public String acceptNewOrder(AcceptNewOrderForm form) {
        R r = odrServiceApi.acceptNewOrder(form);
        String result = MapUtil.getStr(r, "result");
        return result;
    }
}
