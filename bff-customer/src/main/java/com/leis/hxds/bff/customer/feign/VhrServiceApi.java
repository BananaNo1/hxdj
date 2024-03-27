package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.*;
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

    @PostMapping("/voucher/searchUnUseVoucherCount")
    R searchUnUseVoucherCount(SearchUnUseVoucherCountForm form);

    @PostMapping("/voucher/takeVoucher")
    R takeVoucher(TakeVoucherForm form);

    @PostMapping("/voucher/searchBestUnUseVoucher")
    R searchBestUnUseVoucher(SearchBestUnUseVoucherForm form);
}
