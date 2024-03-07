package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.form.ReceiveBillMessageForm;

public interface MessageService {

    String receiveBillMessage(ReceiveBillMessageForm form);
}
