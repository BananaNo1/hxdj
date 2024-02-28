package com.leis.hxds.bff.driver.feign;


import com.leis.hxds.bff.driver.controller.form.RemoveLocationCacheForm;
import com.leis.hxds.bff.driver.controller.form.UpdateLocationCacheForm;
import com.leis.hxds.bff.driver.controller.form.UpdateOrderLocationCacheForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-mps")
public interface MpsServiceApi {

    @PostMapping("/driver/location/updateLocationCache")
    R updateLocationCache(UpdateLocationCacheForm form);

    @PostMapping("/driver/location/removeLocationCache")
    R removeLocationCache(RemoveLocationCacheForm form);

    @PostMapping("/driver/location/updateOrderLocationCache")
    R updateOrderLocationCache(UpdateOrderLocationCacheForm form);
}
