package org.yqj.rabbitmq.demo.springdemo.demo;

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
public class RabbitMqDemo {
    public static void main(String[] args) throws Exception {
        ConnectionFactory cf = buildConnectionFactory();

        // set up the queue, exchange, binding on the broker
        RabbitAdmin admin = new RabbitAdmin(cf);
        Queue queue = new Queue("myQueue");
        admin.declareQueue(queue);
        TopicExchange exchange = new TopicExchange("myExchange");
        admin.declareExchange(exchange);
        admin.declareBinding(
                BindingBuilder.bind(queue).to(exchange).with("foo.*"));

        // set up the listener and container
        SimpleMessageListenerContainer container =
                new SimpleMessageListenerContainer(cf);
        Object listener = new Object() {
            public void handleMessage(String foo) {
                System.out.println(foo);
            }
        };
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(adapter);
        container.setQueueNames("myQueue");
        container.start();

        // send something
        RabbitTemplate template = new RabbitTemplate(cf);
        template.convertAndSend("myExchange", "foo.bar", "Hello, world!");
        Thread.sleep(1000);
        container.stop();
    }

//    public static void main(String[] args) throws Exception{
//
//        ConnectionFactory cf = buildConnectionFactory();
//
//        //set up admin
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(cf);
//
//        // init queue
//        Queue queue = new Queue("myQueue");
//
//        // topic exchange
//        TopicExchange topicExchange = new TopicExchange("TopicExchange");
//
//        // admin bind exchange queue config
//        rabbitAdmin.declareExchange(topicExchange);
//
//        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with("foo.*"));
//
//
//        //set up listener container
//        listenerMessage(cf);
//
//        // send message
//        sendMessageContent(cf);
//    }

    private static void listenerMessage(ConnectionFactory cf) throws Exception{
        //set up listener container
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(cf);
        Object listener = new Object() {
            public void handleMessage(String foo) {
                System.out.println(foo);
            }
        };
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(adapter);
        container.setQueueNames("myQueue");
        container.start();
        Thread.sleep(10000);
        container.stop();
    }

    private static void sendMessageContent(ConnectionFactory cf) throws Exception{
        // send message
        RabbitTemplate template = new RabbitTemplate(cf);
        template.convertAndSend("TopicExchange", "foo.bar", "Hello, Yaoqijun!");
    }

    private static ConnectionFactory buildConnectionFactory(){
        com.rabbitmq.client.ConnectionFactory cf = new com.rabbitmq.client.ConnectionFactory();
        cf.setHost("localhost");
        cf.setPort(5672);
        return new CachingConnectionFactory(cf);
    }
}
