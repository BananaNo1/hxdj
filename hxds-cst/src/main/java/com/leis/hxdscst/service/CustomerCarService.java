package com.leis.hxdscst.service;

import com.leis.hxdscst.db.pojo.CustomerCarEntity;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarService {

    void insertCustomerCar(CustomerCarEntity entity);

    ArrayList<HashMap> searchCustomerCarList(long customerId);

    int deleteCustomerCarById(long id);
}
