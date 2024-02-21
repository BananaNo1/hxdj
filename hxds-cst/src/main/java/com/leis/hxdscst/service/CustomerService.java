package com.leis.hxdscst.service;

import com.leis.hxdscst.db.pojo.CustomerCarEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CustomerService {

    String registerNewCustomer(Map param);

    String login(String code);


}
