package com.leis.hxdsdr.db.dao;


import com.leis.hxdsdr.db.pojo.WalletEntity;

import java.util.HashMap;
import java.util.Map;

public interface WalletDao {
    int insert(WalletEntity entity);

    int updateWalletBalance(Map param);
}




