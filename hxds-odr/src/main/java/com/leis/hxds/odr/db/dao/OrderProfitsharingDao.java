package com.leis.hxds.odr.db.dao;


import com.leis.hxds.odr.db.pojo.OrderProfitsharingEntity;

import java.util.HashMap;
import java.util.Map;

public interface OrderProfitsharingDao {

    int insert(OrderProfitsharingEntity entity);

    HashMap searchDriverIncome(String uuid);

    int updateProfitsharingStatus(long profitsharingId);
}




