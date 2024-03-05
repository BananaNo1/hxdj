package com.leis.hxds.nebula.controller;


import com.leis.hxds.common.util.R;
import com.leis.hxds.nebula.controller.form.CalculateOrderMileageForm;
import com.leis.hxds.nebula.controller.form.InsertOrderGpsForm;
import com.leis.hxds.nebula.controller.form.SearchOrderGpsForm;
import com.leis.hxds.nebula.controller.form.SearchOrderLastGpsForm;
import com.leis.hxds.nebula.service.OrderGpsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/order/gps")
@Tag(name = "OrderGpsController", description = "订单GPG记录Web接口")
public class OrderGpsController {

    @Resource
    private OrderGpsService orderGpsService;

    @PostMapping("/insertOrderGps")
    @Operation(summary = "添加订单GPS记录")
    public R insertOrderGps(@RequestBody @Valid InsertOrderGpsForm form) {
        int rows = orderGpsService.insertOrderGps(form.getList());
        return R.ok().put("rows", rows);
    }

    @PostMapping("/searchOrderGps")
    @Operation(summary = "获取某个订单的GPS定位")
    public R searchOrderGps(@RequestBody @Valid SearchOrderGpsForm form) {
        ArrayList<HashMap> list = orderGpsService.searchOrderGps(form.getOrderId());
        return R.ok().put("result", list);
    }

    @PostMapping("/searchOrderGps")
    @Operation(summary = "获取某个订单最后的GPS定位")
    public R searchOrderLastGps(@RequestBody @Valid SearchOrderLastGpsForm form) {
        HashMap map = orderGpsService.searchOrderLastGps(form.getOrderId());
        return R.ok().put("result", map);
    }

    @PostMapping("/calculateOrderMileage")
    @Operation(summary = "计算里程")
    public R calculateOrderMileage(@RequestBody @Valid CalculateOrderMileageForm form) {
        String mileage = orderGpsService.calculateOrderMileage(form.getOrderId());
        return R.ok().put("result", mileage);
    }
}
