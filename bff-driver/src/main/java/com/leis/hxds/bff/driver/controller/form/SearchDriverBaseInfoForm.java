package com.leis.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询司机基本信息的表单")
public class SearchDriverBaseInfoForm {

    @Schema(description = "司机Id")
    private Long driverId;
}
