package com.leis.hxds.mis.api.feign;

import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.DeleteVoucherByIdsForm;
import com.leis.hxds.mis.api.controller.form.InsertVoucherForm;
import com.leis.hxds.mis.api.controller.form.SearchVoucherByPageForm;
import com.leis.hxds.mis.api.controller.form.UpdateVoucherStatusForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-vhr")
public interface VhrServiceApi {

    @PostMapping("/searchVoucherByPage")
    R searchVoucherByPage(SearchVoucherByPageForm form);

    @PostMapping("/voucher/insertVoucher")
    R insertVoucher(InsertVoucherForm form);

    @PostMapping("/voucher/updateVoucherStatus")
    R updateVoucherStatus(UpdateVoucherStatusForm form);

    @PostMapping("/voucher/deleteVoucherByIds")
    R deleteVoucherByIds(DeleteVoucherByIdsForm form);
}
