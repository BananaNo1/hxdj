package com.leis.hxds.bff.driver.service.impl;

import com.leis.hxds.bff.driver.controller.form.ClearNewOrderQueueForm;
import com.leis.hxds.bff.driver.controller.form.ReceiveNewOrderMessageForm;
import com.leis.hxds.bff.driver.feign.SnmServiceApi;
import com.leis.hxds.bff.driver.service.NewOrderMessageService;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class NewOrderMessageServiceImpl implements NewOrderMessageService {

    @Resource
    private SnmServiceApi snmServiceApi;

    @Override
    public void clearNewOrderQueue(ClearNewOrderQueueForm form) {
        snmServiceApi.clearNewOrderQueue(form);
    }

    @Override
    public ArrayList receiveNewOrderMessage(ReceiveNewOrderMessageForm form) {
        R r = snmServiceApi.receiveNewOrderMessage(form);
        ArrayList list = (ArrayList) r.get("result");
        return list;
    }
}
