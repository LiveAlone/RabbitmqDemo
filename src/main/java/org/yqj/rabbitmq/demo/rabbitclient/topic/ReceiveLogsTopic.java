package org.yqj.rabbitmq.demo.rabbitclient.topic;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

import java.io.IOException;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

/**
 * Created by yaoqijun on 2017-05-11.
 */
public class ReceiveLogsTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    private static final String TOPIC_YAO = "yao.qi.jun";

    private static final String TOPIC_QI = "qi.#";

    public static void Main(String[] args) throws Exception{

        String bindingKey = TOPIC_YAO;

        ConnectionFactory cf = RabbitBuilder.buildConnectionFactory();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, TOPIC);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
