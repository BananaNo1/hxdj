package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;

public interface OrderService {
    int createNewOrder(CreateNewOrderForm form);
}
