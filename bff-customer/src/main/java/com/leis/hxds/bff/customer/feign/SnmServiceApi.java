package com.leis.hxds.bff.customer.feign;

import com.leis.hxds.bff.customer.controller.form.ReceiveBillMessageForm;
import com.leis.hxds.bff.customer.controller.form.SendNewOrderMessageForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-snm")
public interface SnmServiceApi {

    @PostMapping("/message/order/new/sendNewOrderMessageAsync")
    R sendNewOrderMessageAsync(SendNewOrderMessageForm form);

    @PostMapping("/message/receiveBillMessage")
    R receiveBillMessage(ReceiveBillMessageForm form);
}
