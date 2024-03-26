package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.form.SearchUnTakeVoucherByPageForm;
import com.leis.hxds.bff.customer.controller.form.SearchUnUseVoucherByPageForm;
import com.leis.hxds.bff.customer.controller.form.SearchUsedVoucherByPageForm;
import com.leis.hxds.common.util.PageUtils;

public interface VoucherService {

    PageUtils searchUnTakeVoucherByPage(SearchUnTakeVoucherByPageForm form);

    PageUtils searchUnUseVoucherByPage(SearchUnUseVoucherByPageForm form);

    PageUtils searchUsedVoucherByPage(SearchUsedVoucherByPageForm form);
}
