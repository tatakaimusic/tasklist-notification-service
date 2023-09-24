package com.example.tasklistnotificationservice.service;

import com.example.tasklistnotificationservice.model.User;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface MailService {

    void sendRegistrationEmail(User user) throws MessagingException, IOException, TemplateException;
}
