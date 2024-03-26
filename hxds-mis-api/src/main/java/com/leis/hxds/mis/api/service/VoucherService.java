package com.leis.hxds.mis.api.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.mis.api.controller.form.DeleteVoucherByIdsForm;
import com.leis.hxds.mis.api.controller.form.InsertVoucherForm;
import com.leis.hxds.mis.api.controller.form.SearchVoucherByPageForm;
import com.leis.hxds.mis.api.controller.form.UpdateVoucherStatusForm;

public interface VoucherService {

    PageUtils searchVoucherByPage(SearchVoucherByPageForm form);

    int insertVoucher(InsertVoucherForm form);

    int updateVoucherStatus(UpdateVoucherStatusForm form);

    int deleteVoucherByIds(DeleteVoucherByIdsForm form);
}
