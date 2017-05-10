package org.yqj.rabbitmq.demo.rabbitclient.pubsublog;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

import static org.springframework.amqp.core.ExchangeTypes.FANOUT;

/**
 * Created by yaoqijun on 2017/5/11.
 * 提交日志内容，通过 exchange 队列创建
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = RabbitBuilder.buildConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        // declare exchange
        channel.exchangeDeclare(EXCHANGE_NAME, FANOUT);

        for (int i=0; i<10; i++){
            String message = "log messge " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("finish message send " + message);
            Thread.sleep(10000);
        }
        channel.close();
        connection.close();
    }
}
