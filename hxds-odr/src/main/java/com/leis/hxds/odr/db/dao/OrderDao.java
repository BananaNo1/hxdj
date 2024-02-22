package com.leis.hxds.odr.db.dao;


import com.leis.hxds.odr.db.pojo.OrderEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface OrderDao {
    HashMap searchDriverTodayBusinessData(long driverId);

    int insert(OrderEntity entity);

    String searchOrderIdByUUID(String uuid);
}




