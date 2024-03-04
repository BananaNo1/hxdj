package com.leis.hxds.nebula.db.dao;

import com.leis.hxds.nebula.db.pojo.OrderVoiceTextEntity;

import java.util.Map;

public interface OrderVoiceTextDao {

    int insert(OrderVoiceTextEntity entity);

    Long searchIdByUuid(String uuid);

    int updateCheckResult(Map param);
}
