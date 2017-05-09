package org.yqj.rabbitmq.demo.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Created by yaoqijun.
 * Date:2016-10-26
 * Email:yaoqijunmail@gmail.io
 * Descirbe:
 */
public class Send {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // create queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // send message
        String message = "hello world";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("finish to publish message : " + message);

        // close
        channel.close();
        connection.close();
    }

}
