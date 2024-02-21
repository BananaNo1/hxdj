package com.leis.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "查询客户车辆信息的表单")
public class SearchCustomerCarListForm {


    @Schema(description = "客户Id")
    private Long customerId;
}
