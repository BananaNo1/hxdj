package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;
import com.leis.hxds.bff.customer.controller.form.ConfirmArriveStartPlaceForm;
import com.leis.hxds.bff.customer.controller.form.DeleteUnAcceptOrderForm;
import com.leis.hxds.bff.customer.controller.form.HasCustomerCurrentOrderForm;
import com.leis.hxds.bff.customer.controller.form.SearchOrderStatusForm;

import java.util.HashMap;

public interface OrderService {
    HashMap createNewOrder(CreateNewOrderForm form);

    Integer searchOrderStatus(SearchOrderStatusForm form);

    String deleteUnAcceptOrder(DeleteUnAcceptOrderForm form);

    HashMap hasCustomerCurrentOrder(HasCustomerCurrentOrderForm form);

    boolean confirmArriveStartPlace(ConfirmArriveStartPlaceForm form);
}
