package com.leis.hxds.bff.driver.controller;


import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.leis.hxds.bff.driver.controller.form.AcceptNewOrderForm;
import com.leis.hxds.bff.driver.controller.form.SearchDriverCurrentOrderForm;
import com.leis.hxds.bff.driver.controller.form.SearchDriverExecuteOrderForm;
import com.leis.hxds.bff.driver.controller.form.SearchOrderForMoveByIdForm;
import com.leis.hxds.bff.driver.service.OrderService;
import com.leis.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/order")
@Tag(name = "OrderController", description = "订单模块Web接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/acceptNewOrder")
    @SaCheckLogin
    @Operation(summary = "司机接单")
    public R acceptNewOrder(@RequestBody @Valid AcceptNewOrderForm form) {
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        String result = orderService.acceptNewOrder(form);
        return R.ok().put("result", result);
    }

    @PostMapping("/searchDriverExecuteOrder")
    @SaCheckLogin
    @Operation(summary = "查询司机正在执行的订单记录")
    public R searchDriverExecuteOrder(@RequestBody @Valid SearchDriverExecuteOrderForm form) {
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        HashMap map = orderService.searchDriverExecuteOrder(form);
        return R.ok().put("result", map);
    }

    @PostMapping("/searchDriverCurrentOrder")
    @SaCheckLogin
    @Operation(summary = "查询司机当前订单信息")
    public R searchDriverCurrentOrder() {
        long driverId = StpUtil.getLoginIdAsLong();
        SearchDriverCurrentOrderForm form = new SearchDriverCurrentOrderForm();
        form.setDriverId(driverId);
        HashMap map = orderService.searchDriverCurrentOrder(form);
        return R.ok().put("result", map);
    }

    @PostMapping("/searchOrderForMoveById")
    @SaCheckLogin
    @Operation(summary = "查询司机当前订单信息")
    public R searchOrderForMoveById(@RequestBody @Valid SearchOrderForMoveByIdForm form) {
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        HashMap map = orderService.searchOrderForMoveById(form);
        return R.ok().put("result", map);
    }
}
