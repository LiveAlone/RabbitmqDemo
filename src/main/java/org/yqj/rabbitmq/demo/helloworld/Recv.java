package org.yqj.rabbitmq.demo.helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Created by yaoqijun.
 * Date:2016-10-26
 * Email:yaoqijunmail@gmail.io
 * Descirbe:
 */
public class Recv {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
        System.out.println("main thread is over");
    }

}
