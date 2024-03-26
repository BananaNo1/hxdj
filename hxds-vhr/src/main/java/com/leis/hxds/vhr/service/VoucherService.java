package com.leis.hxds.vhr.service;

import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.vhr.db.pojo.VoucherEntity;

import java.util.Map;

public interface VoucherService {
    PageUtils searchVoucherByPage(Map param);

    int insert(VoucherEntity voucherEntity);

    int updateVoucherStatus(Map param);

    int deleteVoucherByIds(Long[] ids);
}
