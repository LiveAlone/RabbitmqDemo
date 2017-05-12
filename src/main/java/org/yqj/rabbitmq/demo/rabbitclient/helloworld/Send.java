package org.yqj.rabbitmq.demo.rabbitclient.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

/**
 * Created by yaoqijun.
 * Date:2016-10-26
 * Email:yaoqijunmail@gmail.io
 * Descirbe:
 */
public class Send {

    private static final String QUEUE_NAME = "hello";

    public static void Main(String[] args) throws Exception{

        ConnectionFactory connectionFactory = RabbitBuilder.buildConnectionFactory();
        Connection connection = connectionFactory.newConnection();

        // create single channel
        Channel channel = connection.createChannel();

        // declare queue name
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 20; i++) {
            String message = "hello yao qi jun" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            Thread.sleep(1000);
        }
        System.out.println(" [X] has send message ");

        channel.close();
        connection.close();
    }

}
