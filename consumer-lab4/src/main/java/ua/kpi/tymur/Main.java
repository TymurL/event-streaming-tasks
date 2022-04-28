package ua.kpi.tymur;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "localhost:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");

        KafkaConsumer<String, Integer> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Pattern.compile("bit\\d+"));

        Map<String, Double> mostPopularItems = new HashMap<>();
        double c = 0.01;

        while (true) {
            ConsumerRecords<String, Integer> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Integer> record : records) {
                mostPopularItems.replaceAll((key, value) -> value * (1 - c));

                mostPopularItems.merge(record.topic(), 1.0, Double::sum);

                mostPopularItems.entrySet().removeIf(entry -> entry.getValue() < 0.5);

                Optional<Map.Entry<String, Double>> max = mostPopularItems
                        .entrySet().stream().max(Map.Entry.comparingByValue());
                max.ifPresent(e ->
                        System.out.println("Most popular item is " + e.getKey() + " with score = " + e.getValue()));
                System.out.println("Popular items = " + mostPopularItems);
                System.out.println();
                Thread.sleep(200);
            }
        }
    }
}
