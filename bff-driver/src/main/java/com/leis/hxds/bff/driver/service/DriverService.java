package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.leis.hxds.bff.driver.controller.form.UpdateDriverAuthForm;

public interface DriverService {

    long registerNewDriver(RegisterNewDriverForm form);

    int updateDriverAuth(UpdateDriverAuthForm form);
}
