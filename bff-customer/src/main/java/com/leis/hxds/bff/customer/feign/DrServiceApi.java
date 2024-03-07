package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.SearchDriverBriefInfoForm;
import com.leis.hxds.bff.customer.controller.form.SearchDriverOpenIdForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-dr")
public interface DrServiceApi {

    @PostMapping("/driver/searchDriverBriefInfo")
    R searchDriverBriefInfo(SearchDriverBriefInfoForm form);

    @PostMapping("/driver/searchDriverOpenId")
    R searchDriverOpenId(SearchDriverOpenIdForm form);
}
