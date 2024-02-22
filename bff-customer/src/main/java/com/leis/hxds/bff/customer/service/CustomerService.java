package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.customer.controller.form.LoginForm;
import com.leis.hxds.bff.customer.controller.form.RegisterNewCustomerForm;

public interface CustomerService {

    long registerNewCustomer(RegisterNewCustomerForm form);

    Long login(LoginForm form);
}
