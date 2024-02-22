package com.leis.hxdscst.db.dao;


import com.leis.hxdscst.db.pojo.CustomerCarEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarDao {

    int insert(CustomerCarEntity customerCarEntity);

    ArrayList<HashMap> searchCustomerCarList(long customerId);

    int deleteCustomerCarById(long id);
}




