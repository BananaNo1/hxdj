package com.leis.hxds.odr.service;

import com.leis.hxds.odr.db.pojo.OrderBillEntity;
import com.leis.hxds.odr.db.pojo.OrderEntity;

import java.util.HashMap;
import java.util.Map;

public interface OrderService {

    HashMap searchDriverTodayBusinessData(long driverId);

    String insertOrder(OrderEntity orderEntity, OrderBillEntity orderBillEntity);

    String acceptNewOrder(long driverId, long orderId);

    HashMap searchDriverExecutorOrder(Map param);

    Integer searchOrderStatus(Map param);

    String deleteAcceptOrder(Map param);
}
