package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.*;
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

    @PostMapping("/order/hasCustomerCurrentOrder")
    R hasCustomerCurrentOrder(HasCustomerCurrentOrderForm form);

    @PostMapping("/order/confirmArriveStartPlace")
    R confirmArriveStartPlace(ConfirmArriveStartPlaceForm form);

    @PostMapping("/order/searchOrderById")
    R searchOrderById(SearchOrderByIdForm form);

    @PostMapping("/order/validCanPayOrder")
    R validCanPayOrder(ValidCanPayOrderForm form);

    @PostMapping("/bill/updateBillPayment")
    R updateBillPayment(UpdateBillPaymentForm form);

    @PostMapping("/order/updateOrderPrepayId")
    R updateOrderPrepayId(UpdateOrderPrepayIdForm form);

    @PostMapping("/order/updateOrderAboutPayment")
    R updateOrderAboutPayment(UpdateOrderAboutPaymentForm form);

    @PostMapping("/comment/insertComment")
    R insertComment(InsertCommentForm form);

    @PostMapping("/order/searchCustomerOrderByPage")
    R searchCustomerOrderByPage(SearchCustomerOrderByPageForm form);
}
