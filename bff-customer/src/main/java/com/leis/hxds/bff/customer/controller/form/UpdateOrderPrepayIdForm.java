package com.leis.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "更新支付订单ID的表单")
public class UpdateOrderPrepayIdForm {

    @Schema(description = "订单Id")
    private Long orderId;

    @Schema(description = "预支付订单Id")
    private String prepayId;
}
