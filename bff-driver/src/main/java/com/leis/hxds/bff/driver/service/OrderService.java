package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.AcceptNewOrderForm;
import com.leis.hxds.bff.driver.controller.form.SearchDriverCurrentOrderForm;
import com.leis.hxds.bff.driver.controller.form.SearchDriverExecuteOrderForm;
import com.leis.hxds.bff.driver.controller.form.SearchOrderForMoveByIdForm;

import java.util.HashMap;

public interface OrderService {

    String acceptNewOrder(AcceptNewOrderForm form);

    HashMap searchDriverExecuteOrder(SearchDriverExecuteOrderForm form);

    HashMap searchDriverCurrentOrder(SearchDriverCurrentOrderForm form);

    HashMap searchOrderForMoveById(SearchOrderForMoveByIdForm form);
}
