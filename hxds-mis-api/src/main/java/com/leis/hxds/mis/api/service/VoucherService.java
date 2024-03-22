package com.leis.hxds.mis.api.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.mis.api.controller.form.SearchVoucherByPageForm;

public interface VoucherService {

    PageUtils searchVoucherByPage(SearchVoucherByPageForm form);
}
