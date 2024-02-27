package com.leis.hxds.bff.driver.feign;


import com.leis.hxds.bff.driver.controller.form.DeleteCustomerCarByIdForm;
import com.leis.hxds.bff.driver.controller.form.InsertCustomerCarForm;
import com.leis.hxds.bff.driver.controller.form.SearchCustomerCarListForm;
import com.leis.hxds.bff.driver.controller.form.SearchCustomerInfoInOrderForm;
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

}
