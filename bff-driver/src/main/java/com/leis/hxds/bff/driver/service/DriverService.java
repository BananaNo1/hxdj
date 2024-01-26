package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.CreateDriverFaceModelForm;
import com.leis.hxds.bff.driver.controller.form.LoginForm;
import com.leis.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.leis.hxds.bff.driver.controller.form.UpdateDriverAuthForm;

import java.util.HashMap;

public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);

    int updateDriverAuth(UpdateDriverAuthForm form);

    String createDriverFaceModel(CreateDriverFaceModelForm form);

    HashMap login(LoginForm form);
}
