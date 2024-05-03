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
    private EmailConfig emailConfig;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JavaMailSender javaMailSender(){
        var javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(emailConfig.getHost());
        javaMailSenderImpl.setPort(Integer.parseInt(emailConfig.getPort()));
        javaMailSenderImpl.setUsername(emailConfig.getUsername());
        javaMailSenderImpl.setPassword(emailConfig.getPassword());
        var props = javaMailSenderImpl.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth.mechanisms", "LOGIN PLAIN");
        return javaMailSenderImpl;
    }

}

