package com.leis.hxds.bff.customer.service.impl;

import com.leis.hxds.bff.customer.controller.form.SearchOrderLocationCacheForm;
import com.leis.hxds.bff.customer.feign.MpsServiceApi;
import com.leis.hxds.bff.customer.service.OrderLocationService;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class OrderLocationServiceImpl implements OrderLocationService {

    @Resource
    private MpsServiceApi mpsServiceApi;

    @Override
    public HashMap searchOrderLocationCache(SearchOrderLocationCacheForm form) {
        R r = mpsServiceApi.searchOrderLocationCache(form);
        HashMap map = (HashMap) r.get("result");
        return map;
    }
}
