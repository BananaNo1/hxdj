package com.leis.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Schema(description = "创建支付订单")
public class CreateWxPaymentForm {

    @NotNull(message = "orderId不能为空")
    @Min(value = 1, message = "orderId不能小于1")
    @Schema(description = "订单Id")
    private Long orderId;

    @Schema(description = "客户Id")
    private Long customerId;

    @Min(value = 1, message = "voucherId不能小于1")
    @Schema(description = "代金券Id")
    private Long voucherId;
}
