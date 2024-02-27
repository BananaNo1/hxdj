package com.leis.hxds.bff.customer.controller.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Schema(description = "删除订单状态的表单")
public class DeleteUnAcceptOrderForm {

    @NotNull(message = "orderId不能为空")
    @Schema(description = "订单Id")
    private Long orderId;

    @Schema(description = "乘客Id")
    private Long customerId;
}
