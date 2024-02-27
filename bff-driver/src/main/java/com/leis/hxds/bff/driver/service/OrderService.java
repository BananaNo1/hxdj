package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.AcceptNewOrderForm;
import com.leis.hxds.bff.driver.controller.form.SearchDriverExecuteOrderForm;

import java.util.HashMap;

public interface OrderService {

    String acceptNewOrder(AcceptNewOrderForm form);

    HashMap searchDriverExecuteOrder(SearchDriverExecuteOrderForm form);
}
