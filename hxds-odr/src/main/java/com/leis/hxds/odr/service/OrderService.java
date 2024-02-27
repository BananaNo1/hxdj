package com.leis.hxds.odr.service;

import com.leis.hxds.odr.db.pojo.OrderBillEntity;
import com.leis.hxds.odr.db.pojo.OrderEntity;

import java.util.HashMap;

public interface OrderService {

    HashMap searchDriverTodayBusinessData(long driverId);

    String insertOrder(OrderEntity orderEntity, OrderBillEntity orderBillEntity);

    String acceptNewOrder(long driverId, long orderId);
}
