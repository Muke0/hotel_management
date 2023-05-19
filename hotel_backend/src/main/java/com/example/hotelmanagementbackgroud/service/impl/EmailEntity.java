package com.example.hotelmanagementbackgroud.service.impl;

import com.example.hotelmanagementbackgroud.service.Emailservice;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailEntity implements Emailservice {
    private final JavaMailSender mailSender;

    public EmailEntity(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean sendemail(String email, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("2273573381@qq.com");
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(text);
        try{
            mailSender.send(msg);
        }catch (MailException ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }
}
