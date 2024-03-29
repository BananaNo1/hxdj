package com.leis.hxds.bff.driver.feign;

import com.leis.hxds.bff.driver.controller.form.*;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-odr")
public interface OdrServiceApi {

    @PostMapping("/order/SearchDriverTodayBusinessData")
    R searchDriverTodayBusinessData(SearchDriverTodayBusinessDataForm Form);

    @PostMapping("/order/acceptNewOrder")
    R acceptNewOrder(AcceptNewOrderForm form);

    @PostMapping("/order/searchDriverExecuteOrder")
    R searchDriverExecuteOrder(SearchDriverExecuteOrderForm form);

    @PostMapping("/order/searchDriverCurrentOrder")
    R searchDriverCurrentOrder(SearchDriverCurrentOrderForm form);

    @PostMapping("/order/searchOrderForMoveById")
    R searchOrderForMoveById(SearchOrderForMoveByIdForm form);

    @PostMapping("/order/arriveStartPlace")
    R arriveStartPlace(ArriveStartPlaceForm form);

    @PostMapping("/order/startDriving")
    R startDriving(StartDrivingForm form);

    @PostMapping("/order/updateOrderStatus")
    R updateOrderStatus(UpdateOrderStatusForm form);

    @PostMapping("/order/validDriverOwnOrder")
    R validDriverOwnOrder(ValidDriverOwnOrderForm form);

    @PostMapping("/order/searchSettlementNeedData")
    R searchSettlementNeedData(SearchSettlementNeedDataForm form);

    @PostMapping("/bill/updateBillFee")
    R updateBillFee(UpdateBillFeeForm form);

    @PostMapping("/bill/searchReviewDriverOrderBill")
    R searchReviewDriverOrderBill(SearchReviewDriverOrderBillForm form);

    @PostMapping("/order/searchOrderStatus")
    R searchOrderStatus(SearchOrderStatusForm form);

    @PostMapping("/order/updateOrderAboutPayment")
    R updateOrderAboutPayment(UpdateOrderAboutPaymentForm form);

    @PostMapping("/order/searchDriverOrderByPage")
    R searchDriverOrderByPage(SearchDriverOrderByPageForm form);

    @PostMapping("/order/searchOrderById")
    R searchOrderById(SearchOrderByIdForm form);

    @PostMapping("/comment/searchCommentByOrderId")
    R searchCommentByOrderId(SearchCommentByOrderIdForm form);
}
