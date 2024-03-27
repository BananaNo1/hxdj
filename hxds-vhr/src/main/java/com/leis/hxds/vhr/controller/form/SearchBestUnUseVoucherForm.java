package com.leis.hxds.vhr.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Schema(description = "查询未使用的最佳代金券")
public class SearchBestUnUseVoucherForm {

    @NotNull(message = "customerId不能为空")
    @Min(value = 1, message = "customerId不能小于1")
    @Schema(description = "乘客Id")
    private Long customerId;

    @NotNull(message = "amount不能为空")
    @Min(value = 0, message = "amount不能小于0")
    @Schema(description = "总金额")
    private BigDecimal amount;

}