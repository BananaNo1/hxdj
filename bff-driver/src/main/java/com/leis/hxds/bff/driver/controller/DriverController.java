package com.leis.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.leis.hxds.bff.driver.controller.form.*;
import com.leis.hxds.bff.driver.service.DriverLocationService;
import com.leis.hxds.bff.driver.service.DriverService;
import com.leis.hxds.bff.driver.service.NewOrderMessageService;
import com.leis.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.websocket.OnError;
import java.util.HashMap;

@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController", description = "司机模块web接口")
public class DriverController {

    @Resource
    private DriverService driverService;

    @Resource
    private DriverLocationService locationService;

    @Resource
    private NewOrderMessageService newOrderMessageService;

    @PostMapping("/registerNewDriver")
    @Operation(summary = "新司机注册")
    public R registerNewDriver(@RequestBody @Valid RegisterNewDriverForm form) {
        long driverId = driverService.registerNewDriver(form);
        StpUtil.login(driverId);
        String tokenValue = StpUtil.getTokenInfo().getTokenValue();
        return R.ok().put("token", tokenValue);
    }

    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新实名认证信息")
    @SaCheckLogin
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form) {
        long driverId = StpUtil.getLoginIdAsLong();
        form.setDriverId(driverId);
        int rows = driverService.updateDriverAuth(form);
        return R.ok().put("rows", rows);
    }

    @PostMapping("/createDriverFaceModel")
    @Operation(summary = "创建司机人脸模型归档")
    @SaCheckLogin
    public R createDriverFaceModel(@RequestBody @Valid CreateDriverFaceModelForm form) {
        long id = StpUtil.getLoginIdAsLong();
        form.setDriverId(id);
        String result = driverService.createDriverFaceModel(form);
        return R.ok().put("result", result);
    }

    @PostMapping("/login")
    @Operation(summary = "登录系统")
    public R login(@RequestBody @Valid LoginForm form) {
        HashMap map = driverService.login(form);
        if (map != null) {
            long driverId = MapUtil.getLong(map, "id");
            //realAuth 1未认证，2已认证，3审核中
            byte realAuth = Byte.parseByte(MapUtil.getStr(map, "realAuth"));
            boolean archive = MapUtil.getBool(map, "archive");
            StpUtil.login(driverId);
            String tokenValue = StpUtil.getTokenInfo().getTokenValue();
            return R.ok().put("token", tokenValue).put("realAuth", realAuth).put("archive", archive);
        }
        return R.ok();
    }

    @GetMapping("/logout")
    @Operation(summary = "退出系统")
    @SaCheckLogin
    public R logout() {
        StpUtil.logout();
        return R.ok();
    }

    @PostMapping("/searchDriverBaseInfo")
    @Operation(summary = "查询司机基本信息")
    @SaCheckLogin
    public R searchDriverBaseInfo() {
        long driverId = StpUtil.getLoginIdAsLong();
        SearchDriverBaseInfoForm form = new SearchDriverBaseInfoForm();
        form.setDriverId(driverId);
        HashMap map = driverService.searchDriverBaseInfo(form);
        return R.ok().put("result", map);
    }

    @PostMapping("/searchWorkBenchData")
    @Operation(summary = "查询司机工作台数据")
    @SaCheckLogin
    public R searchWorkBenchData() {
        long driverId = StpUtil.getLoginIdAsLong();
        HashMap result = driverService.searchWorkbenchData(driverId);
        return R.ok().put("result", result);
    }

    @GetMapping("/searchDriverAuth")
    @Operation(summary = "查询司机认证信息")
    @SaCheckLogin
    public R searchDriverAuth() {
        long driverId = StpUtil.getLoginIdAsLong();
        SearchDriverAuthForm form = new SearchDriverAuthForm();
        form.setDriverId(driverId);
        HashMap map = driverService.searchDriverAuth(form);
        return R.ok().put("result", map);
    }

    @PostMapping("/startWork")
    @Operation(summary = "开始接单")
    @SaCheckLogin
    public R startWork() {
        long driverId = StpUtil.getLoginIdAsLong();

        RemoveLocationCacheForm form1 = new RemoveLocationCacheForm();
        form1.setDriverId(driverId);
        locationService.removeLocationCache(form1);

        ClearNewOrderQueueForm form2 = new ClearNewOrderQueueForm();
        form2.setUserId(driverId);
        newOrderMessageService.clearNewOrderQueue(form2);
        return R.ok();
    }

    @PostMapping("/stopWork")
    @Operation(summary = "停止接单")
    @SaCheckLogin
    public R stopWork() {
        long driverId = StpUtil.getLoginIdAsLong();

        RemoveLocationCacheForm form1 = new RemoveLocationCacheForm();
        form1.setDriverId(driverId);
        locationService.removeLocationCache(form1);

        ClearNewOrderQueueForm form2 = new ClearNewOrderQueueForm();
        form2.setUserId(driverId);
        newOrderMessageService.clearNewOrderQueue(form2);
        return R.ok();
    }


}
