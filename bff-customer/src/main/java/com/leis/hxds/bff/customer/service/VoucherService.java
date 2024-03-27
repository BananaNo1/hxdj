package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.form.*;
import com.leis.hxds.common.util.PageUtils;

public interface VoucherService {

    PageUtils searchUnTakeVoucherByPage(SearchUnTakeVoucherByPageForm form);

    PageUtils searchUnUseVoucherByPage(SearchUnUseVoucherByPageForm form);

    PageUtils searchUsedVoucherByPage(SearchUsedVoucherByPageForm form);

    long searchUnUseVoucherCount(SearchUnUseVoucherCountForm form);

    boolean takeVoucher(TakeVoucherForm form);
}
