package com.leis.hxds.bff.customer.service.impl;

import cn.hutool.core.map.MapUtil;
import com.leis.hxds.bff.customer.controller.form.ReceiveBillMessageForm;
import com.leis.hxds.bff.customer.feign.SnmServiceApi;
import com.leis.hxds.bff.customer.service.MessageService;
import com.leis.hxds.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Resource
    private SnmServiceApi snmServiceApi;

    @Override
    public String receiveBillMessage(ReceiveBillMessageForm form) {
        R r = snmServiceApi.receiveBillMessage(form);
        String msg = MapUtil.getStr(r, "result");
        return msg;
    }
}
