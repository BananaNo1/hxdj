package com.leis.hxds.bff.driver.controller.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@Schema(description = "更新司机认证信息表单")
public class UpdateDriverAuthForm {

    @NotNull(message = "driverId不能为空")
    @Min(value = 1, message = "driverId不能小于1")
    @Schema(description = "司机Id")
    private Long driverId;

    @NotBlank(message = "name不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "name内容不正确")
    @Schema(description = "姓名")
    private String name;

    @Pattern(regexp = "^男$|^女$", message = "性别内容不对")
    @Schema(description = "性别")
    private String sex;

    @NotBlank(message = "pid不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)?\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])\\d{3}(\\d|X|x)$")
    @Schema(description = "身份证")
    private String pid;

    @NotBlank(message = "birthday不能为空")
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$")
    @Schema(description = "生日")
    private String birthday;

    @NotBlank(message = "tel不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "tel内容不正确")
    @Schema(description = "电话")
    private String tel;

    @NotBlank(message = "email不能为空")
    @Email(message = "email内容不正确")
    @Schema(description = "电子信箱")
    private String email;


    @NotBlank(message = "mailAddress不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5\\-]{6,50}$", message = "mailAddress内容不正确")
    private String mailAddress;

    @NotBlank(message = "contactName不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,10}$", message = "contactName内容不正确")
    @Schema(description = "应急联系人")
    private String contactName;

    @NotBlank(message = "contactTel不能为空")
    @Pattern(regexp = "^1\\d{10}$", message = "contactTel内容不正确")
    @Schema(description = "应急联系人电话")
    private String contactTel;

    @NotBlank(message = "idcardAddress不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z\\u4e00-\\u9fa5\\-]{6,50}$", message = "idcardAddress内容不正确")
    @Schema(description = "身份证地址")
    private String idcardAddress;


    @NotBlank(message = "idcardFront不能为空")
    @Schema(description = "身份正面")
    private String idcardFront;

    @NotBlank(message = "idcardBack不能为空")
    @Schema(description = "身份反面")
    private String idcardBack;

    @NotBlank(message = "idcardHolding不能为空")
    @Schema(description = "手持身份证")
    private String idcardHolding;

    @NotBlank(message = "idcardExpiration不能为空")
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "idcardAddress内容不正确")
    @Schema(description = "身份证有效期")
    private String idcardExpiration;

    @NotBlank(message = "drcardType不能为空")
    @Pattern(regexp = "^(A[1-3]|B[12]|C[1-4]|[D-FMNP])$", message = "drcardType内容不正确")
    @Schema(description = "驾驶证类别")
    private String drcardType;

    @NotBlank(message = "drcardExpiration不能为空")
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)?\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])\\d{3}(\\d|X|x)$", message = "drcardExpiration内容不正确")
    @Schema(description = "驾驶证有效期")
    private String drcardExpiration;


    @NotBlank(message = "drcardIssueDate不能为空")
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[01])$")
    @Schema(description = "驾驶证初次领证日期")
    private String drcardIssueDate;

    @NotBlank(message = "drcardFront不能为空")
    @Schema(description = "身份正面")
    private String drcardFront;

    @NotBlank(message = "drcardBack不能为空")
    @Schema(description = "身份反面")
    private String drcardBack;

    @NotBlank(message = "drcardHolding不能为空")
    @Schema(description = "手持身份证")
    private String drcardHolding;
}
