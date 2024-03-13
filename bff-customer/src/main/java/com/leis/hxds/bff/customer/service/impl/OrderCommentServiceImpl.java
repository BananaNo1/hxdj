package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.map.MapUtil;
import com.leis.hxds.bff.customer.controller.form.InsertCommentForm;
import com.leis.hxds.bff.customer.feign.OdrServiceApi;
import com.leis.hxds.bff.customer.service.OrderCommentService;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.common.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderCommentServiceImpl implements OrderCommentService {

    @Resource
    private OdrServiceApi odrServiceApi;

    @Override
    public int insertComment(InsertCommentForm form) {
        R r = odrServiceApi.insertComment(form);
        int rows = MapUtil.getInt(r, "rows");
        if (rows != 1) {
            throw new HxdsException("保存订单评价失败");
        }
        return rows;
    }
}
