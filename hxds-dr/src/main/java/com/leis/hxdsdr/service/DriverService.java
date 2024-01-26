package com.leis.hxdsdr.service;

import java.util.HashMap;
import java.util.Map;

public interface DriverService {

    String registerNewDriver(Map param);

    int updateDriverAuth(Map param);

    String createDriverFaceModel(long driverId, String photo);

    HashMap login(String code);
}
