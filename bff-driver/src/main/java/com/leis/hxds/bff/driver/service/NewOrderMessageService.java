package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.ClearNewOrderQueueForm;
import com.leis.hxds.bff.driver.controller.form.ReceiveNewOrderMessageForm;

import java.util.ArrayList;

public interface NewOrderMessageService {
    void clearNewOrderQueue(ClearNewOrderQueueForm form);

    ArrayList receiveNewOrderMessage(ReceiveNewOrderMessageForm form);

}
