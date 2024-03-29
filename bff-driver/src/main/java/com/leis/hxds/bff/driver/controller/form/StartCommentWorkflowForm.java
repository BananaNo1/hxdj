package com.leis.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "开启评价申诉工作流的表单")
public class StartCommentWorkflowForm {

    @NotNull(message = "orderId不能为空")
    @Min(value = 1, message = "orderId不能小于1")
    @Schema(description = "订单Id")
    private Long orderId;

    @Schema(description = "司机Id")
    private Long driverId;

    @NotNull(message = "customerId不能为空")
    @Min(value = 1, message = "customerId不能小于1")
    @Schema(description = "司机Id")
    private Long customerId;

    @NotBlank(message = "reason不能为空")
    @Schema(description = "申诉原因")
    private String reason;
}
