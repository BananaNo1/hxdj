package com.leis.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "查询司机openId的表单")
public class SearchDriverOpenIdForm {

    @Schema(description = "司机ID")
    private Long driverId;
}
