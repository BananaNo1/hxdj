package com.leis.hxdsdr.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxdsdr.db.dao.WalletDao;
import com.leis.hxdsdr.db.dao.WalletIncomeDao;
import com.leis.hxdsdr.db.dao.WalletPaymentDao;
import com.leis.hxdsdr.db.pojo.WalletIncomeEntity;
import com.leis.hxdsdr.service.WalletIncomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
@Slf4j
public class WalletIncomeServiceImpl implements WalletIncomeService {

    @Resource
    private WalletDao walletDao;
    @Resource
    private WalletIncomeDao walletIncomeDao;

    @Override
    @Transactional
    @LcnTransaction
    public int transfer(WalletIncomeEntity entity) {
        //添加转账记录
        int rows = walletIncomeDao.insert(entity);
        if (rows != 1) {
            throw new HxdsException("添加转账记录失败");
        }

        HashMap param = new HashMap() {{
            put("driverId", entity.getDriverId());
            put("amount", entity.getAmount());
        }};
        //更新帐户余额
        rows = walletDao.updateWalletBalance(param);
        if (rows != 1) {
            throw new HxdsException("更新钱包余额失败");
        }
        return rows;
    }
}
