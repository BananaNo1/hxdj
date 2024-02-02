package com.leis.hxds.bff.driver.feign;

import com.leis.hxds.bff.driver.controller.form.*;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "hxds-dr")
public interface DrServiceApi {

    @PostMapping("/driver/registerNewDriver")
    R registerNewDriver(RegisterNewDriverForm form);

    @PostMapping("/driver/updateDriverAuth")
    R updateDriverAuth(UpdateDriverAuthForm form);

    @PostMapping("/driver/createDriverFaceModel")
    R createDriverFaceModel(CreateDriverFaceModelForm form);

    @PostMapping("/driver/login")
    R login(LoginForm form);

    @PostMapping("/driver/searchDriverBaseInfo")
    R searchDriverBaseInfo(SearchDriverBaseInfoForm form);

    @PostMapping("/driver/searchDriverSetting")
    R searchDriverSetting(SearchDriverSettingForm form);
}
