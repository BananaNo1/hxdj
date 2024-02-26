package com.leis.hxds.bff.driver.feign;

import com.leis.hxds.bff.driver.controller.form.ClearNewOrderQueueForm;
import com.leis.hxds.bff.driver.controller.form.ReceiveNewOrderMessageForm;
import com.leis.hxds.common.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-snm")
public interface SnmServiceApi {

    @PostMapping("/message/order/new/clearNewOrderQueue")
    R clearNewOrderQueue(ClearNewOrderQueueForm form);

    @PostMapping("/message/order/new/receiveNewOrderMessage")
    R receiveNewOrderMessage(ReceiveNewOrderMessageForm form);
}
