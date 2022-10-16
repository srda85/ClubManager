package com.example.clubmanager;

import com.example.clubmanager.email.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@SpringBootApplication
public class ClubManagerApplication {


    public static void main(String[] args) {
        SpringApplication.run(ClubManagerApplication.class, args);
    }


}
