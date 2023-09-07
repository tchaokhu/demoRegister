package com.example.demo.email;

public interface EmailSender {
//    void send(String to, String email);
    String sendMail(String to,String cc, String subject, String body);
}
