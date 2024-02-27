package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.AcceptNewOrderForm;

public interface OrderService {

    String acceptNewOrder(AcceptNewOrderForm form);
}
