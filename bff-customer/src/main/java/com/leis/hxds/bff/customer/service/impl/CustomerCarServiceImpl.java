package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.map.MapUtil;
import com.leis.hxds.bff.driver.controller.form.DeleteCustomerCarByIdForm;
import com.leis.hxds.bff.driver.controller.form.InsertCustomerCarForm;
import com.leis.hxds.bff.driver.controller.form.SearchCustomerCarListForm;
import com.leis.hxds.bff.driver.feign.CstServiceApi;
import com.leis.hxds.bff.driver.service.CustomerCarService;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class CustomerCarServiceImpl implements CustomerCarService {

    @Resource
    private CstServiceApi cstServiceApi;

    @Override
    public void insertCustomerCar(InsertCustomerCarForm form) {
        cstServiceApi.insertCustomerCar(form);
    }

    @Override
    public ArrayList<HashMap> searchCustomerCarList(SearchCustomerCarListForm form) {
        R r = cstServiceApi.searchCustomerCarList(form);
        ArrayList<HashMap> list = (ArrayList<HashMap>) r.get("result");
        return list;
    }

    @Override
    public int deleteCustomerCarById(DeleteCustomerCarByIdForm form) {
        R r = cstServiceApi.deleteCustomerCarById(form);
        int rows = MapUtil.getInt(r, "rows");
        return rows;
    }
}
