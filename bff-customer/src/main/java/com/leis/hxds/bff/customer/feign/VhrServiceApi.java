package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.SearchUnTakeVoucherByPageForm;
import com.leis.hxds.bff.customer.controller.form.SearchUnUseVoucherByPageForm;
import com.leis.hxds.bff.customer.controller.form.SearchUsedVoucherByPageForm;
import com.leis.hxds.bff.customer.controller.form.UseVoucherForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-vhr")
public interface VhrServiceApi {

    @PostMapping("/voucher/customer/userVoucher")
    R userVoucher(UseVoucherForm form);

    @PostMapping("/voucher/searchUnTakeVoucherByPage")
    R searchUnTakeVoucherByPage(SearchUnTakeVoucherByPageForm form);

    @PostMapping("/voucher/searchUnUseVoucherByPage")
    R searchUnUseVoucherByPage(SearchUnUseVoucherByPageForm form);

    @PostMapping("/voucher/searchUsedVoucherByPage")
    R searchUsedVoucherByPage(SearchUsedVoucherByPageForm form);
}
