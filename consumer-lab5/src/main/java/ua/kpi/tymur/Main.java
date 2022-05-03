package ua.kpi.tymur;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");

        KafkaConsumer<String, Integer> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(List.of("topic-lab5"));

        Set<Integer> uniqueNumbers = new HashSet<>();
        int r = 0;
        int i = 0;

        while (true) {
            ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Integer> record : records) {
                uniqueNumbers.add(record.value());

                int n = Integer.numberOfTrailingZeros(record.value().hashCode());
                if (n > r) {
                    r = n;
                }

                i++;
                System.out.println("Size of stream is " + i);
                System.out.println("Number of unique values is " + uniqueNumbers.size());
                System.out.println("Estimation of number of unique values is " + Math.pow(2.0, r));
                System.out.println();
            }
        }
    }
}
