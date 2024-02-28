package com.leis.hxds.bff.driver.service;

import com.leis.hxds.bff.driver.controller.form.RemoveLocationCacheForm;
import com.leis.hxds.bff.driver.controller.form.UpdateLocationCacheForm;
import com.leis.hxds.bff.driver.controller.form.UpdateOrderLocationCacheForm;

public interface DriverLocationService {

    void updateLocationCache(UpdateLocationCacheForm form);

    void removeLocationCache(RemoveLocationCacheForm form);

    void updateOrderLocationCache(UpdateOrderLocationCacheForm form);
}
