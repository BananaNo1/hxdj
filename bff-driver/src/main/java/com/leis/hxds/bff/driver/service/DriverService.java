package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.*;

import java.util.HashMap;

public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);

    int updateDriverAuth(UpdateDriverAuthForm form);

    String createDriverFaceModel(CreateDriverFaceModelForm form);

    HashMap login(LoginForm form);

    HashMap searchDriverBaseInfo(SearchDriverBaseInfoForm form);

    HashMap searchWorkbenchData(long driverId);

    HashMap searchDriverAuth(SearchDriverAuthForm form);


}
