package com.leis.hxds.odr.db.dao;


import com.leis.hxds.odr.db.pojo.OrderCommentEntity;

import java.util.HashMap;
import java.util.Map;

public interface OrderCommentDao {

    int insert(OrderCommentEntity entity);

    HashMap searchCommentByOrderId(Map param);
}




