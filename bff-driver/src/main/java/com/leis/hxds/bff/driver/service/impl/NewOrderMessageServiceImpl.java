package com.leis.hxds.bff.driver.service.impl;

import com.leis.hxds.bff.driver.controller.form.ClearNewOrderQueueForm;
import com.leis.hxds.bff.driver.feign.SnmServiceApi;
import com.leis.hxds.bff.driver.service.NewOrderMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewOrderMessageServiceImpl implements NewOrderMessageService {

    @Resource
    private SnmServiceApi snmServiceApi;

    @Override
    public void clearNewOrderQueue(ClearNewOrderQueueForm form) {
        snmServiceApi.clearNewOrderQueue(form);
    }
}
