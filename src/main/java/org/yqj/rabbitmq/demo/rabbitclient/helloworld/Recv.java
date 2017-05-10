package org.yqj.rabbitmq.demo.rabbitclient.helloworld;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

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

        // build connection factory
        ConnectionFactory cf = RabbitBuilder.buildConnectionFactory();

        // create connection
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();

        // create queue name
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [X] received message : " + message);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }

}
