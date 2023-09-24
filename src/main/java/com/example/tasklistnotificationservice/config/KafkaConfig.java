package com.example.tasklistnotificationservice.config;

import com.jcabi.xml.XML;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.*;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    @Value("${topics}")
    private List<String> topics;

    private final XML settings;

    @Autowired
    public KafkaConfig(XML settings) {
        this.settings = settings;
    }

    @Bean
    public Map<String, Object> receiverProperties() {
        Map<String, Object> props = new HashMap<>(5);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                new TextXPath(this.settings, "//groupId").toString()
        );
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                new TextXPath(this.settings, "//keyDeserializer").toString()
        );
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                new TextXPath(this.settings, "//valueDeserializer").toString()
        );
        props.put(
                "spring.json.trusted.packages",
                new TextXPath(this.settings, "//trustedPackages").toString()
        );
        return props;
    }

    public ReceiverOptions<String, Object> receiverOptions(String topic) {
        ReceiverOptions<String, Object> receiverOptions = ReceiverOptions
                .create(receiverProperties());
        return receiverOptions.subscription(Collections.singleton(topic))
                .addAssignListener(receiverPartitions ->
                        System.out.println("assigned: " + receiverPartitions))   // TODO logs instead println to console
                .addRevokeListener(receiverPartitions ->
                        System.out.println("revoked: " + receiverPartitions));   // TODO logs instead println to console
    }

    @Bean
    public KafkaReceiver<String, Object> kafkaReceiverRegistrationTopic() {
        return KafkaReceiver.create(receiverOptions(topics.get(0)));
    }

    @Bean
    public KafkaReceiver<String, Object> kafkaReceiverReminderTopic() {
        return KafkaReceiver.create(receiverOptions(topics.get(1)));
    }
}
