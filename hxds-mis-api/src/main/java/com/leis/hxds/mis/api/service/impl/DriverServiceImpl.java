package com.leis.hxds.mis.api.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.leis.hxds.common.util.PageUtils;
import com.leis.hxds.common.util.R;
import com.leis.hxds.mis.api.controller.form.SearchDriverByPageForm;
import com.leis.hxds.mis.api.feign.DrServiceApi;
import com.leis.hxds.mis.api.service.DriverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private DrServiceApi drServiceApi;

    @Override
    public PageUtils SearchDriverByPage(SearchDriverByPageForm form) {
        R r = drServiceApi.SearchDriverByPage(form);
        HashMap map = (HashMap) r.get("result");
        PageUtils pageUtils = BeanUtil.toBean(map, PageUtils.class);
        return pageUtils;
    }
}
