package com.leis.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "根据ID查询订单信息的表单")
public class SearchOrderByIdForm {

    @NotNull
    @Min(value = 1,message = "orderId不能小于1")
    @Schema(description = "订单ID")
    private Long orderId;

    @Min(value = 1,message = "customerId不能小于1")
    @Schema(description = "客户ID")
    private Long customerId;

    @Min(value = 1,message = "driverId不能小于1")
    @Schema(description = "司机ID")
    private Long driverId;
}
