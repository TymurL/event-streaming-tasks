package ua.kpi.tymur;

import com.github.javafaker.Faker;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("linger.ms", 1);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        Faker faker = new Faker();

        for (int i = 0; i < 10000; i++) {
            var producerRecord = new ProducerRecord<>(
                    "my-topic",
                    faker.color().name(),
                    String.format("Pokemon %s from %s has been found!",
                            faker.pokemon().name(), faker.pokemon().location())
            );
            producer.send(producerRecord);

            // For first lab delay can be 1 second.
            Thread.sleep(1000);
        }

        producer.close();
    }
}
