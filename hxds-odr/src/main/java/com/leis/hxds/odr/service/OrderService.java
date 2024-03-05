package com.leis.hxds.odr.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.odr.db.pojo.OrderBillEntity;
import com.leis.hxds.odr.db.pojo.OrderEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface OrderService {

    HashMap searchDriverTodayBusinessData(long driverId);

    String insertOrder(OrderEntity orderEntity, OrderBillEntity orderBillEntity);

    String acceptNewOrder(long driverId, long orderId);

    HashMap searchDriverExecutorOrder(Map param);

    Integer searchOrderStatus(Map param);

    String deleteAcceptOrder(Map param);

    HashMap searchDriverCurrentOrder(long driverId);

    HashMap hasCustomerCurrentOrder(long customerId);

    HashMap searchOrderForMoveById(Map param);

    int arriveStartPlate(Map param);

    boolean confirmArriveStartPlace(long orderId);

    int startDriving(Map param);

    int updateOrderStatus(Map param);

    PageUtils searchOrderByPage(Map param);

    HashMap searchOrderContent(long orderId);

    ArrayList<HashMap> searchOrderStartLocationIn30Days();
}
