package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.*;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-cst")
public interface CstServiceApi {
    @PostMapping("/customer/car/insertCustomerCar")
    R insertCustomerCar(InsertCustomerCarForm form);

    @PostMapping("/customer/car/searchCustomerCarList")
    R searchCustomerCarList(SearchCustomerCarListForm form);

    @PostMapping("/customer/car/deleteCustomerCarById")
    R deleteCustomerCarById(DeleteCustomerCarByIdForm form);

    @PostMapping("/customer/searchCustomerInfoInOrder")
    R searchCustomerInfoInOrder(SearchCustomerInfoInOrderForm form);
    @PostMapping("/customer/registerNewCustomer")
    R registerNewCustomer(RegisterNewCustomerForm form);

    @PostMapping("/customer/login")
    R login(LoginForm form);

    @PostMapping("/customer/searchCustomerOpenId")
    R searchCustomerOpenId(SearchCustomerOpenIdForm form);
}
