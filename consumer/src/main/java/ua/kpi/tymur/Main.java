package ua.kpi.tymur;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of("my-topic"));

        Random random = new Random();
        int counter = 0;
        var sample = new ArrayList<String>();

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                if ("black".equals(record.key())) {
                    counter++;
                    if (random.nextInt(10) == 0) {
                        System.out.printf("Add new record to sample: key = %s, value = %s%n",
                                record.key(), record.value());
                        sample.add(record.value());
                        System.out.println("Current sample size is " + sample.size());
                        System.out.println("Total records with key='black' is " + counter);
                    }
                }
            }
        }
    }
}
