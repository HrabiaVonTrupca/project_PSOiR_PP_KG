package com.project_PSOiR_PP_KG.api;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class FactorClient implements AutoCloseable{

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "primes_requests";
    private String replyQueueName = "primes_results";

    public FactorClient() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq.posir.svc.cluster.local");
        factory.setPort(5672);

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public String call(String factorizedNumber) throws IOException, InterruptedException {
//        final String corrId = UUID.randomUUID().toString();
//        String replyQueueName = channel.queueDeclare().getQueue();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
//                .correlationId(corrId)
                .replyTo(replyQueueName)
                .contentType("application/json")
                .build();

        channel.basicPublish("", requestQueueName, props, factorizedNumber.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

//        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
//            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
//                response.offer(new String(delivery.getBody(), "UTF-8"));
//            }
//        }, consumerTag -> {
//        });

        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            response.offer(new String(delivery.getBody(),"UTF-8"));
        }, consumerTag -> {
        });

        String result = response.take();
        channel.basicCancel(ctag);
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}