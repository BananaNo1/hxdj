package com.leis.hxdsdr.service;

import com.leis.hxdsdr.db.pojo.WalletIncomeEntity;

public interface WalletIncomeService {
    int transfer(WalletIncomeEntity entity);
}
