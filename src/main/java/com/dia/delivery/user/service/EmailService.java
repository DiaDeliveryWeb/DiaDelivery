package com.dia.delivery.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;


// mail 서비스 interface
public interface EmailService {

    MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;

    String createKey();

    String sendSimpleMessage(String to) throws Exception;
}