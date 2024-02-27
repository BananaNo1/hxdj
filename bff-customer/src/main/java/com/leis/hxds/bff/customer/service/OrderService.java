package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;
import com.leis.hxds.bff.customer.controller.form.DeleteUnAcceptOrderForm;
import com.leis.hxds.bff.customer.controller.form.SearchOrderStatusForm;

import java.util.HashMap;

public interface OrderService {
    HashMap createNewOrder(CreateNewOrderForm form);

    Integer searchOrderStatus(SearchOrderStatusForm form);

    String deleteUnAcceptOrder(DeleteUnAcceptOrderForm form);
}
