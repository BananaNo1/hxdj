package com.leis.hxds.nebula.db.dao;

import com.leis.hxds.nebula.db.pojo.OrderGpsEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface OrderGpsDao {
    int insert(OrderGpsEntity entity);

    ArrayList<HashMap> searchOrderGps(long orderId);

    HashMap searchOrderLastGps(long orderId);

    ArrayList<HashMap> searchOrderAllGps(long orderId);
}
