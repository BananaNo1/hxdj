package com.leis.hxds.mis.api.feign;

import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.SearchDriverByPageForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-dr")
public interface DrServiceApi {

    @PostMapping("/driver/searchDriverByPage")
    R SearchDriverByPage(SearchDriverByPageForm form);

}
