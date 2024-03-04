package com.leis.hxds.mis.api.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.SearchOrderByPageForm;
import com.leis.hxds.mis.api.controller.form.SearchOrderComprehensiveInfoForm;
import com.leis.hxds.mis.api.controller.form.SearchOrderLastGpsForm;
import com.leis.hxds.mis.api.controller.form.SearchOrderStatusForm;
import com.leis.hxds.mis.api.service.OrderService;
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
@Tag(name = "OrderController", description = "订单管理web接口")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/searchOrderByPage")
    @SaCheckPermission(value = {"ROOT", "ORDER:SELECT"}, mode = SaMode.OR)
    @Operation(summary = "查询订单分页记录")
    public R searchOrderByPage(@RequestBody @Valid SearchOrderByPageForm form) {
        PageUtils pageUtils = orderService.searchOrderByPage(form);
        return R.ok().put("result", pageUtils);
    }

    @PostMapping("/searchOrderComprehensiveInfo")
    @SaCheckPermission(value = {"ROOT", "ORDER:SELECT"}, mode = SaMode.OR)
    @Operation(summary = "查询订单")
    public R searchOrderComprehensiveInfo(@RequestBody @Valid SearchOrderComprehensiveInfoForm form) {
        HashMap result = orderService.searchOrderComprehensiveInfo(form.getOrderId());
        return R.ok().put("result", result);
    }

    @PostMapping("/searchOrderLastGps")
    @SaCheckPermission(value = {"ROOT", "ORDER:SELECT"}, mode = SaMode.OR)
    @Operation(summary = "获取某个订单最后的GPS定位")
    public R searchOrderLastGps(@RequestBody @Valid SearchOrderLastGpsForm form) {
        HashMap result = orderService.searchOrderLastGps(form);
        return R.ok().put("result", result);
    }
}
