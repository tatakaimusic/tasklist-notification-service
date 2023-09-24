package com.example.tasklistnotificationservice.service.impl;

import com.example.tasklistnotificationservice.model.User;
import com.example.tasklistnotificationservice.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements MailService {

    private final Configuration configuration;

    private final JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(Configuration configuration, JavaMailSender mailSender) {
        this.configuration = configuration;
        this.mailSender = mailSender;
    }

    @Override
    public void sendRegistrationEmail(User user) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        helper.setSubject("Thank you for registration, " + user.getUsername());
        helper.setTo(user.getEmail());
        String emailContent = getRegistrationEmailContent(user);
        helper.setText(emailContent, true);
        mailSender.send(mimeMessage);
    }

    private String getRegistrationEmailContent(User user) throws IOException, TemplateException {
        StringWriter writer = new StringWriter();
        Map<String, Object> model = new HashMap<>();
        model.put("name", user.getUsername());
        configuration.getTemplate("register.ftlh")
                .process(model, writer);
        return writer.getBuffer().toString();
    }


}
