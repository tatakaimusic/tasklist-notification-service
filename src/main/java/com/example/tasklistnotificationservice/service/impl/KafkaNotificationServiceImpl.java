package com.example.tasklistnotificationservice.service.impl;

import com.example.tasklistnotificationservice.model.User;
import com.example.tasklistnotificationservice.service.KafkaNotificationService;
import com.example.tasklistnotificationservice.service.MailService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaNotificationServiceImpl implements KafkaNotificationService {

    private final MailService mailService;

    @Autowired
    public KafkaNotificationServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void handleRegistration(User user) throws MessagingException, TemplateException, IOException {
        mailService.sendRegistrationEmail(user);
    }
}
