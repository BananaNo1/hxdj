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
}
