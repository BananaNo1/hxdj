package com.leis.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询司机认证信息表单")
public class SearchDriverAuthForm {

    @Schema(description = "司机ID")
    private Long driverId;
}
