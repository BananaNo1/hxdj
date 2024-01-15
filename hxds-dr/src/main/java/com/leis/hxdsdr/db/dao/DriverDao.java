package com.leis.hxdsdr.db.dao;


import com.leis.hxdsdr.db.pojo.DriverSettingsEntity;
import com.leis.hxdsdr.db.pojo.WalletEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface DriverDao {
    long hasDriver(Map param);

    int registerNewDriver(Map param);

    String searchDriverId(String openId);

}




