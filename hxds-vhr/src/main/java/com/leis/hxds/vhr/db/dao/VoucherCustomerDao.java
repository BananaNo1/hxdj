package com.leis.hxds.vhr.db.dao;


import com.leis.hxds.vhr.db.pojo.VoucherCustomerEntity;

import java.util.Map;

public interface VoucherCustomerDao {
    public int insert(VoucherCustomerEntity entity);

    public String validCanUseVoucher(Map param);

    public int bindVoucher(Map param);

    long searchTakeVoucherNum(Map param);
}




