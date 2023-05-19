package com.example.hotelmanagementbackgroud.service;

public interface Emailservice {
    boolean sendemail(String to, String subject, String text);
}
