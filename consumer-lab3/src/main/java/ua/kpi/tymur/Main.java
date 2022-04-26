package ua.kpi.tymur;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
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

        int sizeOfSample = 5;
        var sample = new String[sizeOfSample];

        int i = 0;

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                if (i < sizeOfSample) {
                    System.out.println("add element at " + i);
                    sample[i] = record.value();
                } else {
                    int j = random.nextInt(i + 1);
                    if (j < sizeOfSample) {
                        System.out.println("replace element at " + i);
                        sample[j] = record.value();
                    }
                }
                System.out.println("sample = " + Arrays.toString(sample));
                i++;
            }
        }
    }
}
