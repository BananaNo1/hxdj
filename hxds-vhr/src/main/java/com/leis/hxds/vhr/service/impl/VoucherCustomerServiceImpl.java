package com.leis.hxds.vhr.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.vhr.db.dao.VoucherCustomerDao;
import com.leis.hxds.vhr.service.VoucherCustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class VoucherCustomerServiceImpl implements VoucherCustomerService {

    @Resource
    private VoucherCustomerDao voucherCustomerDao;

    @Override
    @Transactional
    @LcnTransaction
    public String useVoucher(Map param) {
        String discount = voucherCustomerDao.validCanUseVoucher(param);
        if (discount != null) {
            int rows = voucherCustomerDao.bindVoucher(param);
            if (rows != 1) {
                throw new HxdsException("代金券不可用");
            }
            return discount;
        }
        throw new HxdsException("代金券不可用");
    }
}
