package com.leis.hxds.snm;

import com.leis.hxds.snm.entity.NewOrderMessage;
import com.leis.hxds.snm.task.NewOrderMessageTask;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Demo {

    @Resource
    private NewOrderMessageTask task;

    @Test
    public void send() {
        NewOrderMessage message = new NewOrderMessage();
        message.setUserId("9527");
        message.setFrom("沈阳东站");
        message.setFrom("沈阳站");
        message.setFrom("3.2");
        message.setFrom("46.0");
        message.setFrom("18.6");
        message.setFrom("18");
        message.setFrom("0.0");
        ArrayList list = new ArrayList() {{
            add(message);
        }};
        task.sendNewOrderMessageAsync(list);
    }

    @Test
    public void receive() {
        List<NewOrderMessage> list = task.receiveNewOrderMessage(9527);
        list.forEach(one -> {
            System.out.println(one.getFrom());
            System.out.println(one.getTo());
        });

    }


}
