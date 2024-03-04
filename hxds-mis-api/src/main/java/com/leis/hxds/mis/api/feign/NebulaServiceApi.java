package com.leis.hxds.mis.api.feign;

import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.SearchOrderGpsForm;
import com.leis.hxds.mis.api.controller.form.SearchOrderLastGpsForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-nebula")
public interface NebulaServiceApi {

    @PostMapping("/order/gps/searchOrderGps")
    R searchOrderGps(SearchOrderGpsForm form);

    @PostMapping("/order/gps/searchOrderLastGps")
    R searchOrderLastGps(SearchOrderLastGpsForm form);
}
