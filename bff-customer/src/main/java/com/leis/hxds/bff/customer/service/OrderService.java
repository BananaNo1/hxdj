package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.CreateNewOrderForm;
import com.leis.hxds.bff.customer.controller.form.*;
import com.leis.hxds.common.util.PageUtils;

import java.util.HashMap;

public interface OrderService {
    HashMap createNewOrder(CreateNewOrderForm form);

    Integer searchOrderStatus(SearchOrderStatusForm form);

    String deleteUnAcceptOrder(DeleteUnAcceptOrderForm form);

    HashMap hasCustomerCurrentOrder(HasCustomerCurrentOrderForm form);

    boolean confirmArriveStartPlace(ConfirmArriveStartPlaceForm form);

    HashMap searchOrderById(SearchOrderByIdForm form);

    HashMap createWxPayment(long orderId, long customerId, Long voucherId);

    String updateOrderAboutPayment(UpdateOrderAboutPaymentForm form);

    PageUtils searchCustomerOrderByPage(SearchCustomerOrderByPageForm form);
}
