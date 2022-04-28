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

        int numberOfStreams = 100;

        while (true) {
            var n = random.nextInt(numberOfStreams);
            for (int i = 0; i < numberOfStreams; i++) {
                var producerRecord = new ProducerRecord<>("bit" + i, i == n ? 1 : 0);
                producer.send(producerRecord);
            }
            Thread.sleep(2500);
        }
    }
}
