package com.example.tasklistnotificationservice.config;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class BeanConfig {

    @Bean
    public XML consumerXML() throws FileNotFoundException {
        return new XMLDocument(
                new File("src/main/resources/kafka/consumer.xml")
        );
    }
}
