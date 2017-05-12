package org.yqj.rabbitmq.demo.rabbitclient.taskworker;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.yqj.rabbitmq.demo.rabbitclient.RabbitBuilder;

/**
 * Created by yaoqijun on 2017/5/11.
 * 任务创建方式， Task 不同的创建方式
 */
public class TaskCreator {

    public static final String TASK_QUEUE_NAME = "task_queue";

    public static void Main(String[] args) throws Exception{

        // create channel
        ConnectionFactory cf = RabbitBuilder.buildConnectionFactory();
        Connection connection = cf.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        for (int i = 0; i < 20; i++){
            System.out.println("start to send message index " + i);

            String message = createMessage() + i;

            channel.basicPublish("",
                    TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message.getBytes("UTF-8"));

            System.out.println("finish to send message index " + i);
            Thread.sleep(5000);
        }

        channel.close();
        connection.close();

    }

    private static String createMessage(){
        return "create task message content";
    }

}
