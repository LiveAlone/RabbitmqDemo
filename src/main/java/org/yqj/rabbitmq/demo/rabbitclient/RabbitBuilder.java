package org.yqj.rabbitmq.demo.rabbitclient;

import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by yaoqijun on 2017/5/11.
 */
public class RabbitBuilder {

    public static ConnectionFactory buildConnectionFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        return connectionFactory;
    }

}
