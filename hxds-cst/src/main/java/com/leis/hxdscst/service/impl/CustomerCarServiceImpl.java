package com.leis.hxdscst.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.leis.hxdscst.db.dao.CustomerCarDao;
import com.leis.hxdscst.db.pojo.CustomerCarEntity;
import com.leis.hxdscst.service.CustomerCarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CustomerCarServiceImpl implements CustomerCarService {

    @Resource
    private CustomerCarDao customerCarDao;

    @Override
    @Transactional
    @LcnTransaction
    public void insertCustomerCar(CustomerCarEntity entity) {
        customerCarDao.insert(entity);
    }

    @Override
    public ArrayList<HashMap> searchCustomerCarList(long customerId) {
        ArrayList<HashMap> list = customerCarDao.searchCustomerCarList(customerId);
        return list;
    }

    @Override
    public int deleteCustomerCarById(long id) {
        int rows = customerCarDao.deleteCustomerCarById(id);
        return rows;
    }
}
