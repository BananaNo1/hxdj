package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.LoginForm;
import com.leis.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-cst")
public interface CstServiceApi {

    @PostMapping("/customer/registerNewCustomer")
    R registerNewCustomer(RegisterNewCustomerForm form);

    @PostMapping("/customer/login")
    R login(LoginForm form);
}
