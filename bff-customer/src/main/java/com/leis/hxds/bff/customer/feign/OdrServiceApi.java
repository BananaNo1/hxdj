package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.InsertOrderForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-odr")
public interface OdrServiceApi {
    @PostMapping("/order/insertOrder")
    R insertOrder(InsertOrderForm form);
}
