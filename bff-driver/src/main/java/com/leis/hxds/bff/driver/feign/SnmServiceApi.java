package com.leis.hxds.bff.driver.feign;

import com.leis.hxds.bff.driver.controller.form.ClearNewOrderQueueForm;
import com.leis.hxds.bff.driver.controller.form.ReceiveNewOrderMessageForm;
import com.leis.hxds.bff.driver.controller.form.SendPrivateMessageForm;
import com.leis.hxds.common.util.R;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "hxds-snm")
public interface SnmServiceApi {

    @PostMapping("/message/order/new/clearNewOrderQueue")
    R clearNewOrderQueue(ClearNewOrderQueueForm form);

    @PostMapping("/message/order/new/receiveNewOrderMessage")
    R receiveNewOrderMessage(ReceiveNewOrderMessageForm form);

    @PostMapping("/message/sendPrivateMessage")
    @Operation(summary = "同步发送私有消息")
    R sendPrivateMessage(SendPrivateMessageForm form);

    @PostMapping("/message/sendPrivateMessageAsync")
    @Operation(summary = "异步发送私有消息")
    R sendPrivateMessageAsync(SendPrivateMessageForm form);
}
