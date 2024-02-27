package com.leis.hxdscst.service.impl;

import cn.hutool.core.map.MapUtil;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.MicroAppUtil;
import com.leis.hxdscst.db.dao.CustomerDao;
import com.leis.hxdscst.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Resource
    private CustomerDao customerDao;

    @Resource
    private MicroAppUtil microAppUtil;

    @Override
    @Transactional
    @LcnTransaction
    public String registerNewCustomer(Map param) {
        String code = MapUtil.getStr(param, "code");
        String openId = microAppUtil.getOpenId(code);
        HashMap tempParam = new HashMap() {{
            put("openId", openId);
        }};
        if (customerDao.hasCustomer(tempParam) != 0) {
            throw new HxdsException("该微信无法注册");
        }
        param.put("openId", openId);
        customerDao.registerNewCustomer(param);
        String customerId = customerDao.searchCustomerId(openId);
        return customerId;
    }

    @Override
    public String login(String code) {
        String openId = microAppUtil.getOpenId(code);
        String customerId = customerDao.login(openId);
        customerId = (customerId != null ? customerId : "");
        return customerId;
    }

    @Override
    public HashMap searchCustomerInfoInOrder(long customerId) {
        HashMap map = customerDao.searchCustomerInfoInOrder(customerId);
        return map;
    }


}
