package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;

import java.util.HashMap;

public interface OrderService {
    HashMap createNewOrder(CreateNewOrderForm form);
}
