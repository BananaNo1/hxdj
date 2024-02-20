package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.leis.hxds.bff.customer.controller.form.LoginForm;
import com.leis.hxds.bff.customer.controller.form.RegisterNewCustomerForm;
import com.leis.hxds.bff.customer.feign.CstServiceApi;
import com.leis.hxds.bff.customer.service.CustomerService;
import com.leis.hxds.common.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CstServiceApi cstServiceApi;

    @Override
    public long registerNewCustomer(RegisterNewCustomerForm form) {
        R r = cstServiceApi.registerNewCustomer(form);
        long userId = Convert.toLong(r.get("userId"));
        return userId;
    }

    @Override
    public Long login(LoginForm form) {
        R r = cstServiceApi.login(form);
        String userId = MapUtil.getStr(r, "userId");
        if (!StrUtil.isBlank(userId)) {
            return Convert.toLong(userId);
        }
        return null;
    }
}
