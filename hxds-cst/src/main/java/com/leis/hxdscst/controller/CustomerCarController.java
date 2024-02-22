package com.leis.hxdscst.controller;


import cn.hutool.core.bean.BeanUtil;
import com.leis.hxds.common.util.R;
import com.leis.hxdscst.controller.form.DeleteCustomerCarByIdForm;
import com.leis.hxdscst.controller.form.InsertCustomerCarForm;
import com.leis.hxdscst.controller.form.SearchCustomerCarListForm;
import com.leis.hxdscst.db.pojo.CustomerCarEntity;
import com.leis.hxdscst.service.CustomerCarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/customer/car")
@Tag(name = "CustomerCarController", description = "客户车辆web接口")
public class CustomerCarController {

    @Resource
    private CustomerCarService customerCarService;

    @PostMapping("/insertCustomerCar")
    @Operation(summary = "添加客户车辆")
    public R insertCustomerCar(@RequestBody @Valid InsertCustomerCarForm form) {
        CustomerCarEntity entity = BeanUtil.toBean(form, CustomerCarEntity.class);
        customerCarService.insertCustomerCar(entity);
        return R.ok();
    }

    @PostMapping("/searchCustomerCarList")
    @Operation(summary = "查询客户车辆信息列表")
    public R searchCustomerCarList(@RequestBody @Valid SearchCustomerCarListForm form) {
        ArrayList<HashMap> list = customerCarService.searchCustomerCarList(form.getCustomerId());
        return R.ok().put("result", list);
    }

    @PostMapping("/deleteCustomerCarById")
    @Operation(summary = "删除客户车辆")
    public R deleteCustomerCarById(@RequestBody @Valid DeleteCustomerCarByIdForm form) {
        int rows = customerCarService.deleteCustomerCarById(form.getId());
        return R.ok().put("rows", rows);
    }

}
