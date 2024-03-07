package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;
import com.leis.hxds.bff.customer.controller.form.*;

import java.util.HashMap;

public interface OrderService {
    HashMap createNewOrder(CreateNewOrderForm form);

    Integer searchOrderStatus(SearchOrderStatusForm form);

    String deleteUnAcceptOrder(DeleteUnAcceptOrderForm form);

    HashMap hasCustomerCurrentOrder(HasCustomerCurrentOrderForm form);

    boolean confirmArriveStartPlace(ConfirmArriveStartPlaceForm form);

    HashMap searchOrderById(SearchOrderByIdForm form);

    HashMap createWxPayment(long orderId, long customerId, Long voucherId);
}
