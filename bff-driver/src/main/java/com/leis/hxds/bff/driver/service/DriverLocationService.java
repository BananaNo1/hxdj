package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.RemoveLocationCacheForm;
import com.leis.hxds.bff.driver.controller.form.UpdateLocationCacheForm;

public interface DriverLocationService {

    void updateLocationCache(UpdateLocationCacheForm form);

    void removeLocationCache(RemoveLocationCacheForm form);
}
