package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.ClearNewOrderQueueForm;

public interface NewOrderMessageService {
    void clearNewOrderQueue(ClearNewOrderQueueForm form);
}
