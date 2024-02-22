package com.leis.hxds.bff.customer.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Schema(description = "查询符合某个订单接单的司机列表的表单")
public class SearchBefittingDriverAboutOrderForm {


    @NotBlank(message = "startPlaceLatitude不能为空")
    @Pattern(regexp = "^(([1-8]\\d?)|([1-8]\\d))(\\.\\d{1,18})|90|0(\\.\\d{1,18})?$", message =
            "startPlaceLatitude" + "内容不正确")
    @Schema(description = "订单起点的维度")
    private String startPlaceLatitude;


    @NotBlank(message = "startPlaceLongitude不能为空")
    @Pattern(regexp = "^(([1-9]\\d?)|(1[0-7]\\d))(\\.\\d{1,18})|180|0(\\.\\d{1,18})?$", message =
            "startPlaceLongitude内容不正确")
    @Schema(description = "订单起点的经度")
    private String startPlaceLongitude;


    @NotBlank(message = "endPlaceLatitude不能为空")
    @Pattern(regexp = "^(([1-8]\\d?)|([1-8]\\d))(\\.\\d{1,18})|90|0(\\.\\d{1,18})?$", message = "endPlaceLatitude" +
            "内容不正确")
    @Schema(description = "订单终点的维度")
    private String endPlaceLatitude;

    @NotBlank(message = "endPlaceLongitude不能为空")
    @Pattern(regexp = "^(([1-9]\\d?)|(1[0-7]\\d))(\\.\\d{1,18})|180|0(\\.\\d{1,18})?$", message =
            "endPlaceLongitude" + "内容不正确")
    @Schema(description = "订单终点的经度")
    private String endPlaceLongitude;

    @NotBlank(message = "mileage不能为空")
    @Pattern(regexp = "^[1-9]\\d*\\.\\d+$|^0\\.\\d*[1-9]\\d*$|^[1-9]\\d*$", message = "mileage内容不正确")
    @Schema(description = "预估里程")
    private String mileage;

}
