package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.*;

import java.util.HashMap;

public interface OrderService {

    String acceptNewOrder(AcceptNewOrderForm form);

    HashMap searchDriverExecuteOrder(SearchDriverExecuteOrderForm form);

    HashMap searchDriverCurrentOrder(SearchDriverCurrentOrderForm form);

    HashMap searchOrderForMoveById(SearchOrderForMoveByIdForm form);

    int arriveStartPlace(ArriveStartPlaceForm form);

    int startDriving(StartDrivingForm form);

    int updateOrderStatus(UpdateOrderStatusForm form);

    int updateOrderBill(UpdateBillFeeForm form);

    HashMap searchReviewDriverOrderBill(SearchReviewDriverOrderBillForm form);
}
