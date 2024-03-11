package com.leis.hxds.odr.feign;

import com.leis.hxds.common.util.R;
import com.leis.hxds.odr.controller.form.TransferForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-api")
public interface DrServiceApi {

    @PostMapping("/wallet/income/transfer")
    R transfer(TransferForm form);
}
