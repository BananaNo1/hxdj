package com.leis.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "检查订单是否可以支付的表单")
public class ValidCanPayOrderForm {

    @Schema(description = "订单Id")
    private Long orderId;

    @Schema(description = "客户Id")
    private Long customerId;
}
