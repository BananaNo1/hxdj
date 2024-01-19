package com.leis.hxdsdr.controller;

import cn.hutool.core.bean.BeanUtil;
import com.leis.hxds.common.util.R;
import com.leis.hxdsdr.controller.form.RegisterNewDriverForm;
import com.leis.hxdsdr.controller.form.UpdateDriverAuthForm;
import com.leis.hxdsdr.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/driver")
@Tag(name = "DriverController", description = "司机模块Web接口")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/registerNewDriver")
    @Operation(summary = "新司机注册")
    public R registerNewDriver(@RequestBody @Valid RegisterNewDriverForm form) {
        Map param = BeanUtil.beanToMap(form);
        String userId = driverService.registerNewDriver(param);
        return R.ok().put("userId", userId);
    }


    @PostMapping("/updateDriverAuth")
    @Operation(summary = "更新实名信息")
    public R updateDriverAuth(@RequestBody @Valid UpdateDriverAuthForm form) {
        Map map = BeanUtil.beanToMap(form);
        int rows = driverService.updateDriverAuth(map);
        return R.ok().put("rows", rows);
    }
}
