package com.leis.hxds.bff.customer.service;

import com.leis.hxds.bff.driver.controller.form.DeleteCustomerCarByIdForm;
import com.leis.hxds.bff.driver.controller.form.InsertCustomerCarForm;
import com.leis.hxds.bff.driver.controller.form.SearchCustomerCarListForm;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarService {

    void insertCustomerCar(InsertCustomerCarForm form);

    ArrayList<HashMap> searchCustomerCarList(SearchCustomerCarListForm form);

    int deleteCustomerCarById(DeleteCustomerCarByIdForm form);
}
