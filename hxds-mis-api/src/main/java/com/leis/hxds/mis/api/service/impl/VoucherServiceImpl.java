package com.leis.hxds.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.InsertVoucherForm;
import com.leis.hxds.mis.api.controller.form.SearchVoucherByPageForm;
import com.leis.hxds.mis.api.feign.VhrServiceApi;
import com.leis.hxds.mis.api.service.VoucherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Resource
    private VhrServiceApi vhrServiceApi;

    @Override
    public PageUtils searchVoucherByPage(SearchVoucherByPageForm form) {
        R r = vhrServiceApi.searchVoucherByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    @Transactional
    @LcnTransaction
    public int insertVoucher(InsertVoucherForm form) {
        R r = vhrServiceApi.insertVoucher(form);
        int rows = MapUtil.getInt(r, "rows");
        return rows;
    }
}
