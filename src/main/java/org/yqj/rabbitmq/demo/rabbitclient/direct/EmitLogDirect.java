package org.yqj.rabbitmq.demo.rabbitclient.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

import static org.springframework.amqp.core.ExchangeTypes.DIRECT;

/**
 * Created by yaoqijun on 2017-05-11.
 * 日志消息通知机制
 */
public class EmitLogDirect {

    public static final String EXCHANGE_NAME = "direct_logs";

    private static final String ERROR_ROUTE = "error_queue";

    private static final String INFO_ROUTE = "info_queue";

    private static final String ERROR_LOG = "error log content";

    private static final String INFO_LOG = "info log content";

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = RabbitBuilder.buildConnectionFactory();
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, DIRECT);

        for (int i =0; i< 20; i++){
            channel.basicPublish(EXCHANGE_NAME, ERROR_ROUTE, null, (ERROR_LOG+i).getBytes("UTF-8"));
            System.out.println("finish error send content");
            Thread.sleep(1000);

            channel.basicPublish(EXCHANGE_NAME, INFO_ROUTE, null, (INFO_LOG+i).getBytes("UTF-8"));
            System.out.println("finish info send content");
            Thread.sleep(1000);
        }
        channel.close();
        connection.close();
    }

}
