package com.leis.hxdscst.controller;

import cn.hutool.core.bean.BeanUtil;
import com.leis.hxds.common.util.R;
import com.leis.hxdscst.controller.form.LoginForm;
import com.leis.hxdscst.controller.form.RegisterNewCustomerForm;
import com.leis.hxdscst.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@Tag(name = "CustomerController", description = "客户web接口")
public class CustomerController {

    @Resource
    private CustomerService customerService;

    @PostMapping("/registerNewCustomer")
    @Operation(summary = "注册新司机")
    public R registerCustomer(@RequestBody @Valid RegisterNewCustomerForm form) {
        Map param = BeanUtil.beanToMap(form);
        String userId = customerService.registerNewCustomer(param);
        return R.ok().put("userId", userId);
    }

    @PostMapping("/login")
    @Operation(summary = "登录系统")
    public R login(@RequestBody @Valid LoginForm form) {
        String userId = customerService.login(form.getCode());
        return R.ok().put("userId", userId);
    }
}
