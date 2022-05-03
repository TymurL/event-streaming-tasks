package ua.kpi.tymur;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

        Producer<Object, Integer> producer = new KafkaProducer<>(props);

        Random random = new Random();

        while (true) {
            var n = random.nextDouble() < 0.5
                    ? random.nextInt(250) + 1
                    : 1;
            var producerRecord = new ProducerRecord<>("topic-lab5", n);
            producer.send(producerRecord);
            Thread.sleep(200);
        }
    }
}
