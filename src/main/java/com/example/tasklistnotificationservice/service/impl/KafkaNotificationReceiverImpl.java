package com.example.tasklistnotificationservice.service.impl;

import com.example.tasklistnotificationservice.model.User;
import com.example.tasklistnotificationservice.service.KafkaNotificationReceiver;
import com.example.tasklistnotificationservice.service.KafkaNotificationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
public class KafkaNotificationReceiverImpl implements KafkaNotificationReceiver {

    private final KafkaReceiver<String, Object> kafkaReceiver;

    private final KafkaNotificationService kafkaNotificationService;

    @Autowired
    public KafkaNotificationReceiverImpl(KafkaReceiver<String, Object> kafkaReceiver, KafkaNotificationService kafkaNotificationService) {
        this.kafkaReceiver = kafkaReceiver;
        this.kafkaNotificationService = kafkaNotificationService;
    }

    @PostConstruct
    private void init() {
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder().create();
        kafkaReceiver.receive().subscribe(r -> {
            User user = gson.fromJson(r.value().toString(), User.class);
            kafkaNotificationService.handle(user);
            r.receiverOffset().acknowledge();
        });
    }
}
