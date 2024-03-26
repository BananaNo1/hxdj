package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.leis.hxds.bff.customer.controller.form.SearchUnTakeVoucherByPageForm;
import com.leis.hxds.bff.customer.controller.form.SearchUnUseVoucherByPageForm;
import com.leis.hxds.bff.customer.controller.form.SearchUsedVoucherByPageForm;
import com.leis.hxds.bff.customer.feign.VhrServiceApi;
import com.leis.hxds.bff.customer.service.VoucherService;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Resource
    private VhrServiceApi vhrServiceApi;

    @Override
    public PageUtils searchUnTakeVoucherByPage(SearchUnTakeVoucherByPageForm form) {
        R r = vhrServiceApi.searchUnTakeVoucherByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    public PageUtils searchUnUseVoucherByPage(SearchUnUseVoucherByPageForm form) {
        R r = vhrServiceApi.searchUnUseVoucherByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }

    @Override
    public PageUtils searchUsedVoucherByPage(SearchUsedVoucherByPageForm form) {
        R r = vhrServiceApi.searchUsedVoucherByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }
}
