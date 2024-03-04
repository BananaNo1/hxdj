package com.leis.hxds.nebula.db.dao;

import com.leis.hxds.nebula.db.pojo.OrderMonitoringEntity;

import java.util.HashMap;

public interface OrderMonitoringDao {
    int insert(long orderId);

    HashMap searchOrderRecordAndReviews(long orderId);

    int updateOrderMonitoring(OrderMonitoringEntity entity);
}
