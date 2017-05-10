package org.yqj.rabbitmq.demo.springdemo;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Created by yaoqijun on 2017/5/10.
 */
public class RabbitMqDemoSender {
    public static void main(String[] args) throws Exception {
        ConnectionFactory cf = buildConnectionFactory();

        // set up the queue, exchange, binding on the broker
        RabbitAdmin admin = new RabbitAdmin(cf);
        Queue queue = new Queue("myQueue4");
        admin.declareQueue(queue);
        TopicExchange exchange = new TopicExchange("myExchange4", true, true);
        admin.declareExchange(exchange);
        admin.declareBinding(
                BindingBuilder.bind(queue).to(exchange).with("foo.*"));
        // send something
        RabbitTemplate template = new RabbitTemplate(cf);
        for (int i = 0; i < 10; i++) {
            template.convertAndSend("myExchange4", "foo.bar", "Hello, yao qi jun!");
        }
    }

    private static ConnectionFactory buildConnectionFactory(){
        com.rabbitmq.client.ConnectionFactory cf = new com.rabbitmq.client.ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);
        return new CachingConnectionFactory(cf);
    }
}
