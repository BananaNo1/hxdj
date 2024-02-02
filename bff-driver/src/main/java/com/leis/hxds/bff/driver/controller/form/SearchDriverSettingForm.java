package com.leis.hxds.bff.driver.controller.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询司机设置的表单")
public class SearchDriverSettingForm {

    @Schema(description = "司机Id")
    private long driverId;
}
