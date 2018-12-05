package com.lht.calllog.consumer;

import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class Producer_Test {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("metadata.broker.list","192.168.135.117:9092");
        properties.setProperty("serializer.class","kafka.serializer.StringEncoder");
        properties.put("request.required.acks","1");
        ProducerConfig config = new ProducerConfig(properties);
        kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(config);
        KeyedMessage<String, String> data = new KeyedMessage<String, String>("ha1","我想看看成功了没有 ");
        producer.send(data);
    }
}
