package org.yqj.rabbitmq.demo.rabbitclient.direct;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

import java.io.IOException;

import static org.springframework.amqp.core.ExchangeTypes.DIRECT;

/**
 * Created by yaoqijun on 2017-05-11.
 * 日志消息通知机制
 */
public class ReceiveLogDirect {

    private static final String EXCHANGE_NAME = "direct_logs";

    private static final String ERROR_ROUTE = "error_queue";

    private static final String INFO_ROUTE = "info_queue";

    public static void main(String[] argv) throws Exception {

        String routeKey = INFO_ROUTE;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, DIRECT);
        String queueRouteQueueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueRouteQueueName, EXCHANGE_NAME, routeKey);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueRouteQueueName, true, consumer);
    }

}
