package org.yqj.rabbitmq.demo.springdemo.demo;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by yaoqijun on 2017-05-12.
 */
public class SimpleRabbitmqDemo {
    public static void Main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory);
        amqpAdmin.declareQueue(new Queue("MyQueue"));
        AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        amqpTemplate.convertAndSend("MyQueue", "foo");
        String foo = (String) amqpTemplate.receiveAndConvert("MyQueue");
        System.out.println(foo);
    }
}
