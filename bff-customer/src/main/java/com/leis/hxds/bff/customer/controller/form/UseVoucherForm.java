package com.leis.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "使用代金券的表单")
public class UseVoucherForm {

    @NotNull(message = "voucherId不能为空")
    @Min(value = 1, message = "voucherId不能小于1")
    @Schema(description = "代金券Id")
    private Long voucherId;

    @NotNull(message = "customerId不能为空")
    @Min(value = 1, message = "customerId不能小于1")
    @Schema(description = "客户Id")
    private Long customerId;

    @NotNull(message = "orderId不能为空")
    @Min(value = 1, message = "orderId不能小于1")
    @Schema(description = "订单Id")
    private Long orderId;

    @Schema(description = "订单金额")
    private String amount;
}
