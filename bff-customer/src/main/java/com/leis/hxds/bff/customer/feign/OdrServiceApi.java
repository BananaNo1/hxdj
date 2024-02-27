package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.DeleteUnAcceptOrderForm;
import com.leis.hxds.bff.customer.controller.form.InsertOrderForm;
import com.leis.hxds.bff.customer.controller.form.SearchOrderStatusForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-odr")
public interface OdrServiceApi {
    @PostMapping("/order/insertOrder")
    R insertOrder(InsertOrderForm form);

    @PostMapping("/order/searchOrderStatus")
    R searchOrderStatus(SearchOrderStatusForm form);
    
    @PostMapping("/order/deleteUnAcceptOrder")
    R deleteUnAcceptOrder(DeleteUnAcceptOrderForm form);
}
