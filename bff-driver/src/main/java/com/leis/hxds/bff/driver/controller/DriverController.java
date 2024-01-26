package com.leis.hxds.bff.driver.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.map.MapUtil;
import com.leis.hxds.bff.driver.controller.form.CreateDriverFaceModelForm;
import com.leis.hxds.bff.driver.controller.form.LoginForm;
import com.leis.hxds.bff.driver.controller.form.RegisterNewDriverForm;
import com.leis.hxds.bff.driver.controller.form.UpdateDriverAuthForm;
import com.leis.hxds.bff.driver.service.DriverService;
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
@RequestMapping("/driver")
@Tag(name = "DriverController", description = "司机模块web接口")
public class DriverController {

    @Resource
    private DriverService driverService;

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
            byte realAuth = Byte.parseByte(MapUtil.getStr(map, "realAuth"));
            boolean archive = MapUtil.getBool(map, "archive");
            StpUtil.login(driverId);
            String tokenValue = StpUtil.getTokenInfo().getTokenValue();
            return R.ok().put("token", tokenValue).put("realAuth", realAuth).put("archive", archive);
        }
        return R.ok();
    }
}
