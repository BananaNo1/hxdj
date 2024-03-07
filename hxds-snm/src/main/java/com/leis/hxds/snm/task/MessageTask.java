package com.leis.hxds.snm.task;

import com.leis.hxds.common.exception.HxdsException;
import com.leis.hxds.snm.db.pojo.MessageEntity;
import com.leis.hxds.snm.db.pojo.MessageRefEntity;
import com.leis.hxds.snm.service.MessageService;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class MessageTask {

    @Resource
    private ConnectionFactory factory;

    @Resource
    private MessageService messageService;


    /**
     * 同步发送私有消息
     */
    public void sendPrivateMessage(String identity, long userId, Integer ttl, MessageEntity entity) {
        String id = messageService.insertMessage(entity);
        String exchangeName = identity + "_private";
        String queueName = "queue_" + userId;
        String routingKey = userId + "";
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            HashMap param = new HashMap<>();
            channel.queueDeclare(queueName, true, false, false, param);
            HashMap map = new HashMap();
            map.put("messageId", id);

            AMQP.BasicProperties properties;
            if (ttl != null && ttl > 0) {
                properties =
                        new AMQP.BasicProperties().builder().contentEncoding("UTF-8").headers(map).expiration(ttl +
                                "").build();
            } else {
                properties = new AMQP.BasicProperties().builder().contentEncoding("UTF-8").headers(map).build();
            }
            channel.basicPublish(exchangeName, routingKey, properties, entity.getMsg().getBytes());
            log.debug("消息发送成功");
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("向MQ发送消息失败");
        }
    }

    /**
     * 异步发送私有消息
     */
    @Async
    public void sendPrivateMessageAsync(String identity, long userId, Integer ttl, MessageEntity entity) {
        sendPrivateMessage(identity, userId, ttl, entity);
    }

    public void sendBroadcastMessage(String identity, Integer ttl, MessageEntity entity) {
        String id = messageService.insertMessage(entity);
        String exchangeName = identity + "_broadcast";
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            HashMap map = new HashMap();
            map.put("messageId", id);
            AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
            builder.deliveryMode(MessageDeliveryMode.toInt(MessageDeliveryMode.PERSISTENT)); //消息持久化
            builder.expiration(ttl.toString()); // 设置消息有效期
            AMQP.BasicProperties properties = builder.headers(map).build();

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);
            channel.basicPublish(exchangeName, "", properties, entity.getMsg().getBytes());
            log.debug("消息发送成功");
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("向MQ发送消息失败");
        }
    }

    @Async
    public void sendBroadcastMessageAsync(String identity, Integer ttl, MessageEntity entity) {
        sendBroadcastMessage(identity, ttl, entity);
    }

    /**
     * 同步接受消息
     */
    public int receive(String identity, long userId) {
        String privateExchangeName = identity + "_private";
        String broadcastExchangeName = identity + "_broadcast";
        String queueName = "queue_" + userId;
        String routingKey = userId + "";

        int i = 0;
        try (Connection connection = factory.newConnection(); Channel privateChannel = connection.createChannel(); Channel broadcastChannel = connection.createChannel()) {
            //接受私有消息
            privateChannel.exchangeDeclare(privateExchangeName, BuiltinExchangeType.DIRECT);
            privateChannel.queueDeclare(queueName, true, false, false, null);
            privateChannel.queueBind(queueName, privateExchangeName, routingKey);
            while (true) {
                GetResponse response = privateChannel.basicGet(queueName, false);
                if (response != null) {
                    AMQP.BasicProperties properties = response.getProps();
                    Map<String, Object> map = properties.getHeaders();
                    String messageId = map.get("messageId").toString();
                    byte[] body = response.getBody();
                    String message = new String(body);
                    log.debug("从RabbitMq接收消息：" + message);

                    MessageRefEntity refEntity = new MessageRefEntity();
                    refEntity.setMessageId(messageId);
                    refEntity.setReceiverId(userId);
                    refEntity.setReceiverIdentity(identity);
                    refEntity.setReadFlag(false);
                    refEntity.setLastFlag(true);
                    messageService.insertRef(refEntity);
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    privateChannel.basicAck(deliveryTag, false);
                    i++;
                } else {
                    break;
                }
            }
            //接受公有消息
            broadcastChannel.exchangeDeclare(broadcastExchangeName, BuiltinExchangeType.FANOUT);
            broadcastChannel.queueDeclare(queueName, true, false, false, null);
            broadcastChannel.queueBind(queueName, broadcastExchangeName, "");
            while (true) {
                GetResponse response = broadcastChannel.basicGet(queueName, false);
                if (response != null) {
                    AMQP.BasicProperties properties = response.getProps();
                    Map<String, Object> map = properties.getHeaders();
                    String messageId = map.get("messageId").toString();
                    byte[] body = response.getBody();
                    String message = new String(body);
                    log.debug("从RabbitMq接收的广播消息：" + message);
                    MessageRefEntity refEntity = new MessageRefEntity();
                    refEntity.setMessageId(messageId);
                    refEntity.setReceiverId(userId);
                    refEntity.setReceiverIdentity(identity);
                    refEntity.setReadFlag(false);
                    refEntity.setLastFlag(true);
                    messageService.insertRef(refEntity);
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    broadcastChannel.basicAck(deliveryTag, false);
                    i++;
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("接受消息失败");
        }
        return i;
    }

    /**
     * 异步接受消息
     */
    @Async
    public void receiveAsync(String identity, long userId) {
        receive(identity, userId);
    }

    /**
     * 同步删除消息队列
     */
    public void deleteQueue(String identity, long userId) {
        String privateExchangeName = identity + "_private";
        String broadcastExchangeName = identity + "_broadcast";
        String queueName = "queue_" + userId;
        try (Connection connection = factory.newConnection(); Channel privateChannel = connection.createChannel(); Channel broadcastChannel = connection.createChannel()) {

            privateChannel.exchangeDeclare(privateExchangeName, BuiltinExchangeType.DIRECT);
            privateChannel.queueDelete(queueName);

            broadcastChannel.exchangeDeclare(broadcastExchangeName, BuiltinExchangeType.FANOUT);
            broadcastChannel.queueDelete(queueName);
            log.debug("删除消息队列成功");
        } catch (Exception e) {
            log.error("删除消息队列失败", e);
            throw new HxdsException("删除队列失败");
        }
    }

    /**
     * 异步删除消息队列
     */
    @Async
    public void deleteQueueAsync(String identity, long userId) {
        deleteQueue(identity, userId);
    }


    public String receiveBillMessage(String identity, long userId) {
        String exchangeName = identity + "_private";//交换机名字
        String queueName = "queue_" + userId;
        String routingKey = userId + "";
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();) {

            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, routingKey);
            channel.basicQos(0, 1, true);

            GetResponse response = channel.basicGet(queueName, false);
            if (response != null) {
                AMQP.BasicProperties properties = response.getProps();
                Map<String, Object> map = properties.getHeaders();
                String messageId = map.get("messageId").toString();
                byte[] body = response.getBody();
                String msg = new String(body);
                log.debug("从RabbitMq接受到订单消息" + msg);

                //把接收到的消息保存在MessageRef集合
                MessageRefEntity entity = new MessageRefEntity();
                entity.setMessageId(messageId);
                entity.setReceiverId(userId);
                entity.setReceiverIdentity(identity);
                entity.setReadFlag(true);
                entity.setLastFlag(true);
                messageService.insertRef(entity);

                long deliveryTag = response.getEnvelope().getDeliveryTag();
                channel.basicAck(deliveryTag, false);
                return msg;
            }
            return "";
        } catch (Exception e) {
            log.error("执行异常", e);
            throw new HxdsException("接受新订单失败");
        }


    }
}
