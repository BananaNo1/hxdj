package com.leis.hxds.snm.config;

import com.rabbitmq.client.ConnectionFactory;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq:host}")
    private String host;
    @Value("${rabbitmq:port}")
    private int port;

    @Value("${rabbitmq:username}")
    private String username;

    @Value("${rabbitmq:password}")
    private String password;


    @Bean
    public ConnectionFactory getFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
/*        connectionFactory.setUsername(username);
         connectionFactory.setPassword(password);*/
        return connectionFactory;
    }

}
