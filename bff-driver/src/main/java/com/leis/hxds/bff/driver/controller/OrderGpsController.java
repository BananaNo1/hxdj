package com.leis.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.leis.hxds.bff.driver.controller.form.InsertOrderGpsForm;
import com.leis.hxds.bff.driver.service.OrderGpsService;
import com.leis.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/order/gps")
@Tag(name = "OrderGpsController", description = "订单GPS记录Web接口")
public class OrderGpsController {

    @Resource
    private OrderGpsService orderGpsService;


    @PostMapping("/insertOrderGps")
    @SaCheckLogin
    @Operation(summary = "添加订单GPS记录")
    public R insertOrderGps(@RequestBody @Valid InsertOrderGpsForm form) {
        long driverId = StpUtil.getLoginIdAsLong();
        form.getList().forEach(one -> {
            one.setDriverId(driverId);
        });
        int rows = orderGpsService.insertOrderGps(form);
        return R.ok().put("rows", rows);
    }

}
