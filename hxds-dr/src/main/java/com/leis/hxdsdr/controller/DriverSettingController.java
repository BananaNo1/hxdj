package com.leis.hxdsdr.controller;

import com.leis.hxds.common.util.R;
import com.leis.hxdsdr.controller.form.SearchDriverSettingForm;
import com.leis.hxdsdr.service.DriverSettingService;
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
@RequestMapping("/setting")
@Tag(name = "SettingController", description = "司机设置模块Web接口")
public class DriverSettingController {

    @Resource
    private DriverSettingService driverSettingService;

    @PostMapping("/searchDriverSettings")
    @Operation(summary = "查询司机的设置")
    public R searchSetting(@RequestBody @Valid SearchDriverSettingForm form) {
        HashMap result = driverSettingService.searchDriverSettings(form.getDriverId());
        return R.ok().put("result", result);
    }

}
