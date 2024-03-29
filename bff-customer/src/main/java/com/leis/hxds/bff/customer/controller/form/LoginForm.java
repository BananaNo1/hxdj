package com.leis.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "登录系统")
public class LoginForm {

    @NotBlank(message = "code不能为空")
    @Schema(description = "微信小程序临时授权")
    private String code;
}
