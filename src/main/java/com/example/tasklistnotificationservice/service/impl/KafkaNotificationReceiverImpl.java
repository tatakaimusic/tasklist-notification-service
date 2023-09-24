package com.example.tasklistnotificationservice.service.impl;

import com.example.tasklistnotificationservice.model.User;
import com.example.tasklistnotificationservice.service.KafkaNotificationReceiver;
import com.example.tasklistnotificationservice.service.KafkaNotificationService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import freemarker.template.TemplateException;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

import java.io.IOException;

@Service
public class KafkaNotificationReceiverImpl implements KafkaNotificationReceiver {

    private final KafkaReceiver<String, Object> kafkaReceiverRegistrationTopic;

    private final KafkaReceiver<String, Object> kafkaReceiverReminderTopic;

    private final KafkaNotificationService kafkaNotificationService;

    @Autowired
    public KafkaNotificationReceiverImpl(KafkaReceiver<String, Object> kafkaReceiverRegistrationTopic, KafkaReceiver<String, Object> kafkaReceiverReminderTopic, KafkaNotificationService kafkaNotificationService) {
        this.kafkaReceiverRegistrationTopic = kafkaReceiverRegistrationTopic;
        this.kafkaReceiverReminderTopic = kafkaReceiverReminderTopic;
        this.kafkaNotificationService = kafkaNotificationService;
    }

    @PostConstruct
    private void init() {
        fetch();
    }

    @Override
    public void fetch() {
        Gson gson = new GsonBuilder().create();
        kafkaReceiverRegistrationTopic.receive().subscribe(r -> {
            User user = gson.fromJson(r.value().toString(), User.class);
            try {
                kafkaNotificationService.handleRegistration(user);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            r.receiverOffset().acknowledge();
        });
    }
}
