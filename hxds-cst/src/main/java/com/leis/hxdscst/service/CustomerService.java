package com.leis.hxdscst.service;

import java.util.Map;

public interface CustomerService {

    String registerNewCustomer(Map param);

    String login(String code);
}
