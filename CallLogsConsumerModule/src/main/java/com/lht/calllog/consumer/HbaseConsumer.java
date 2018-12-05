package com.lht.calllog.consumer;

import com.lht.calllog.consumer.HbaseDao;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 消费者
 * 我修改了注释文件
 冲突测试
 *
 */

public class HbaseConsumer {
    public static void main(String[] args) {
        int b =1;
        int m=2;
        int c = 1;
        int f = 1;
        HbaseDao dao = new HbaseDao();
        //配置相应的属性
        Properties pro = new Properties();
        pro.put("zookeeper.connect", "es02:2181");
        //配置消费组
        pro.put("group.id", "c2");
        //配置从头消费
        pro.put("auto.offset.reset", "smallest");
        //创建消费者配置对象
        ConsumerConfig config = new ConsumerConfig(pro);
        //创建消费者连接对象
        ConsumerConnector connector = Consumer.createJavaConsumerConnector(config);
        //创建Map，主要用来存储多个topic信息
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        //配置线程数和主题。
        hashMap.put("calllog", 2);
        //创建获取信息流
        Map<String, List<KafkaStream<byte[], byte[]>>> messageStreams =
                connector.createMessageStreams(hashMap);
        //取出对应topic的流
        List<KafkaStream<byte[], byte[]>> kafkaStreams = messageStreams.get("calllog");
        //循环接收map内的topic数据
        for (final KafkaStream<byte[], byte[]> stream : kafkaStreams) {
            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            while (iterator.hasNext()) {
                MessageAndMetadata<byte[], byte[]> next = iterator.next();
                //接受kafka中的消息
                String msg = new String(next.message());
                //写入到hbase中
                dao.put(msg);
            }
        }
    }
}