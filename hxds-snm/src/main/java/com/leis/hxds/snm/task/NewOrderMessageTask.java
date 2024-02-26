package com.leis.hxds.snm.task;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.snm.entity.NewOrderMessage;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NewOrderMessageTask {

    @Resource
    private ConnectionFactory factory;

    /**
     * 同步发送订单消息
     */
    public void sendNewOrderMessage(ArrayList<NewOrderMessage> list) {
        int ttl = 1 * 60 * 1000; // 新订单消息缓存过期时间1分钟
        String exchangeName = "new_order_private"; //交换机的名字
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel();) {
            //定义交换机，根据routing key路由消息
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            HashMap param = new HashMap();
            for (NewOrderMessage message : list) {
                //MQ消息的属性信息
                HashMap map = new HashMap();
                map.put("orderId", message.getOrderId());
                map.put("from", message.getFrom());
                map.put("to", message.getTo());
                map.put("expectsFee", message.getExpectsFee());
                map.put("mileage", message.getMileage());
                map.put("minute", message.getMinute());
                map.put("distance", message.getDistance());
                map.put("favourFee", message.getFavourFee());
                //创建消息属性对象
                AMQP.BasicProperties properties =
                        new AMQP.BasicProperties().builder().contentEncoding("UTF-8").headers(map).expiration(ttl +
                                "").build();

                String queueName = "queue_" + message.getUserId();
                String routingKey = message.getUserId();
                channel.queueDeclare(queueName, true, false, false, param);
                channel.queueBind(queueName, exchangeName, routingKey);
                channel.basicPublish(exchangeName, routingKey, properties, ("新订单" + message.getOrderId()).getBytes());
                log.debug(message.getUserId() + "新订单消息发送成功");
            }
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("新订单消息发送失败");
        }
    }

    /**
     * 异步发送新订单消息
     *
     * @param list
     */
    @Async
    public void sendNewOrderMessageAsync(ArrayList<NewOrderMessage> list) {
        sendNewOrderMessage(list);
    }

    /**
     * 同步接受新订单消息
     *
     * @param userId
     * @return
     */
    public List<NewOrderMessage> receiveNewOrderMessage(long userId) {
        String exchangeName = "new_order_private";
        String queueName = "queue_" + userId;
        String routingKey = userId + "";

        List<NewOrderMessage> list = new ArrayList<>();
        try (Connection connection = factory.newConnection(); Channel privateChannel = connection.createChannel();) {
            privateChannel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            privateChannel.queueDeclare(queueName, true, false, false, null);
            privateChannel.queueBind(queueName, exchangeName, routingKey);
            privateChannel.basicQos(0, 10, true);

            while (true) {
                GetResponse response = privateChannel.basicGet(queueName, false);
                if (response != null) {
                    AMQP.BasicProperties properties = response.getProps();
                    Map<String, Object> map = properties.getHeaders();
                    String orderId = MapUtil.getStr(map, "orderId");
                    String from = MapUtil.getStr(map, "from");
                    String to = MapUtil.getStr(map, "to");
                    String expectsFee = MapUtil.getStr(map, "expectsFee");
                    String mileage = MapUtil.getStr(map, "mileage");
                    String minute = MapUtil.getStr(map, "minute");
                    String distance = MapUtil.getStr(map, "distance");
                    String favourFee = MapUtil.getStr(map, "favourFee");

                    NewOrderMessage message = new NewOrderMessage();
                    message.setOrderId(orderId);
                    message.setFrom(from);
                    message.setTo(to);
                    message.setExpectsFee(expectsFee);
                    message.setMileage(mileage);
                    message.setMinute(minute);
                    message.setDistance(distance);
                    message.setFavourFee(favourFee);

                    list.add(message);

                    byte[] body = response.getBody();
                    String msg = new String(body);
                    log.debug("从RabbitMq接受消息：" + msg);

                    //
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    privateChannel.basicAck(deliveryTag, false);
                } else {
                    break;
                }
            }
            ListUtil.reverse(list);
            return list;
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("接受新订单消息失败");
        }
    }

    /**
     * 同步删除新订单消息队列
     *
     * @param userId
     */
    public void deleteNewOrderQueue(long userId) {
        String exchangeName = "new_order_private";
        String queueName = "queue_" + userId;
        try (Connection connection = factory.newConnection(); Channel privateChannel = connection.createChannel();) {
            privateChannel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            privateChannel.queueDelete(queueName);
            log.debug(userId + "的新订单消息队列成功删除");
        } catch (Exception e) {
            log.error(userId + "的新订单队列删除失败" + e);
            throw new HxdsException("删除新订单消息失败");
        }
    }

    /**
     * 异步删除新订单消息队列
     *
     * @param userId
     */
    @Async
    public void deleteNewOrderQueueAsync(long userId) {
        deleteNewOrderQueue(userId);
    }

    /**
     * 同步清空新订单消息队列
     *
     * @param userId
     */
    public void clearNewOrderQueue(long userId) {
        String exchangeName = "new_order_private";
        String queueName = "queue_" + userId;
        try (Connection connection = factory.newConnection(); Channel privateChannel = connection.createChannel();) {
            privateChannel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            privateChannel.queuePurge(queueName);
            log.debug(userId + "的新订单消息队列清空删除");
        } catch (Exception e) {
            log.error(userId + "的新订单队列清空失败" + e);
            throw new HxdsException("新订单消息清空失败");
        }
    }

    /**
     * 异步清空新订单消息队列
     *
     * @param userId
     */
    @Async
    public void clearNewOrderQueueAsync(long userId) {
        clearNewOrderQueue(userId);
    }

}
