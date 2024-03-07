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

    int updateDriverAuth(Map param);

    HashMap searchDriverNameAndSex(long driverId);

    int updateDriverArchive(long driverId);

    HashMap login(String openId);

    HashMap searchDriverBaseInfo(long driverId);

    ArrayList<HashMap> searchDriverByPage(Map param);

    long searchDriverCount(Map param);

    HashMap searchDriverAuth(long driverId);

    HashMap searchDriverRealSummary(long driverId);

    int updateDriverRealAuth(Map param);

    HashMap searchDriverBriefInfo(long driverId);

    String searchDriverOpenId(long driverId);
}




