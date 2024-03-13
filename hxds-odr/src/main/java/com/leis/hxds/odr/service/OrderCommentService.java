package com.leis.hxds.odr.service;

import com.leis.hxds.odr.db.pojo.OrderCommentEntity;

import java.util.HashMap;
import java.util.Map;

public interface OrderCommentService {
    int insert(OrderCommentEntity entity);

    HashMap searchCommentByOrderId(Map param);
}
