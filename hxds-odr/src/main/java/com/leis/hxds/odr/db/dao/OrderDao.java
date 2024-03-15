package com.leis.hxds.odr.db.dao;


import com.leis.hxds.odr.db.pojo.OrderEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface OrderDao {
    HashMap searchDriverTodayBusinessData(long driverId);

    int insert(OrderEntity entity);

    String searchOrderIdByUUID(String uuid);

    int acceptNewOrder(Map param);

    HashMap searchDriverExecutorOrder(Map param);

    Integer searchOrderStatus(Map param);

    int deleteAcceptOrder(Map param);

    HashMap searchDriverCurrentOrder(long driverId);

    Long hashCustomerUnFinishedOrder(long customerId);

    HashMap hashCustomerUnAcceptOrder(long customerId);

    HashMap searchOrderForMoveById(Map param);

    int updateOrderStatus(Map param);

    long searchOrderCount(Map param);

    ArrayList<HashMap> searchOrderByPage(Map param);

    HashMap searchOrderContent(long orderId);

    ArrayList<String> searchOrderStartLocationIn30Days();

    int updateOrderMileageAndFee(Map param);

    long validDriverOwnOrder(Map param);

    HashMap searchSettlementNeedData(long orderId);

    HashMap searchOrderById(Map param);

    HashMap validCanPayOrder(Map param);

    int updateOrderPrepayId(Map param);

    HashMap searchOrderIdAndStatus(String uuid);

    HashMap searchDriverIdAndIncentiveFee(String uuid);

    int updateOrderPayIdAndStatus(Map param);

    int finishOrder(String uuid);

    HashMap searchUuidAndStatus(long orderId);

    int updateOrderAboutPayment(Map param);

    long validDriverAndCustomerOwnOrder(Map param);

    ArrayList<HashMap> searchDriverOrderByPage(Map param);

    long searchDriverOrderCount(Map param);

    ArrayList<HashMap> searchCustomerOrderByPage(Map param);

    long searchCustomerOrderCount(Map param);
}




