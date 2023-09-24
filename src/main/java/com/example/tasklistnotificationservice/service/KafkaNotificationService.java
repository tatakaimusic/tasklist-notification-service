package com.example.tasklistnotificationservice.service;

import com.example.tasklistnotificationservice.model.User;

public interface KafkaNotificationService {

    void handle(User user);
}
