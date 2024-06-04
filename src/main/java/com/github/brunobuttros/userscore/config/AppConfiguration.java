package com.github.brunobuttros.userscore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

    @Autowired
    private EmailConfiguration emailConfiguration;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        var javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(emailConfiguration.getHost());
        javaMailSenderImpl.setPort(Integer.parseInt(emailConfiguration.getPort()));
        javaMailSenderImpl.setUsername(emailConfiguration.getUsername());
        javaMailSenderImpl.setPassword(emailConfiguration.getPassword());
        var props = javaMailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth.mechanisms", "LOGIN PLAIN");
        return javaMailSenderImpl;
    }

}

