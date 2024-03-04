package com.leis.hxds.bff.driver.feign;

import com.leis.hxds.bff.driver.config.MultiPartSupportConfig;
import com.leis.hxds.bff.driver.controller.form.InsertOrderGpsForm;
import com.leis.hxds.bff.driver.controller.form.InsertOrderMonitoringForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "hxds-nebula", configuration = MultiPartSupportConfig.class)
public interface NebulaServiceApi {

    @PostMapping(value = "/monitoring/updateRecordFile")
    R uploadRecodeFile(@RequestPart(value = "file") MultipartFile file, @RequestPart(value = "name") String name,
                       @RequestPart(value = "text", required = false) String text);

    @PostMapping(value = "/monitoring/insertOrderMonitoring")
    R insertOrderMonitoring(InsertOrderMonitoringForm form);

    @PostMapping("/order/gps/insertOrderGps")
    R insertOrderGps(InsertOrderGpsForm form);

}
