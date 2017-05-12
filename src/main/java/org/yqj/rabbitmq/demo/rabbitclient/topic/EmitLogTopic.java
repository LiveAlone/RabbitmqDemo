package org.yqj.rabbitmq.demo.rabbitclient.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

import static org.springframework.amqp.core.ExchangeTypes.TOPIC;

/**
 * Created by yaoqijun on 2017-05-11.
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    private static final String TOPIC_YAO = "yao.#";

    private static final String TOPIC_QI = "qi.#";

    public static void Main(String[] args) throws Exception {

        ConnectionFactory cf = RabbitBuilder.buildConnectionFactory();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, TOPIC);

        for (int i=0; i<2; i++){
            String message = "emit message " + i;

            channel.basicPublish(EXCHANGE_NAME, "yao.jun", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + "yao.jun" + "':'" + message + "'");
            Thread.sleep(10000);

            channel.basicPublish(EXCHANGE_NAME, "qi.jun", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + "qi.jun" + "':'" + message + "'");
            Thread.sleep(10000);

            channel.basicPublish(EXCHANGE_NAME, "yao.qi.jun", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + "yao.qi.jun" + "':'" + message + "'");
            Thread.sleep(10000);
        }
        channel.close();
        connection.close();
    }
}
