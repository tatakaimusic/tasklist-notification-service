package com.example.tasklistnotificationservice.service.impl;

import com.example.tasklistnotificationservice.model.User;
import com.example.tasklistnotificationservice.service.KafkaNotificationService;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificationServiceImpl implements KafkaNotificationService {
    @Override
    public void handle(User user) {
        System.out.println(user.toString()); //test reading from kafka
    }
}
