package com.leis.hxdsdr.service;

import com.leis.hxds.common.util.PageUtils;

import java.util.HashMap;
import java.util.Map;

public interface DriverService {

    String registerNewDriver(Map param);

    int updateDriverAuth(Map param);

    String createDriverFaceModel(long driverId, String photo);

    HashMap login(String code);

    HashMap searchDriverBaseInfo(long driverId);

    PageUtils searchDriverByPage(Map param);

    HashMap searchDriverAuth(long driverId);
}
