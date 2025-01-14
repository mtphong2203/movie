package com.jaf.movietheater.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private SpringTemplateEngine templateEngine;

    // Lấy địa chỉ email và tên người gửi từ application.properties
    @Value("${email.from}")
    private String fromEmail;

    @Value("${email.from.name}")
    private String fromName;
    
    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, false);
            messageHelper.setFrom(fromEmail, fromName);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(text);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setFrom(fromEmail, fromName);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(htmlBody, true); // true để gửi nội dung HTML
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send HTML email: " + e.getMessage());
        }
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody, File attachment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            helper.addAttachment(attachment.getName(), attachment);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email with attachment: " + e.getMessage());
        }
    }

    public void sendAccountCreationEmail(String to, String username, String password) {
        try {
            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("password", password);

            String htmlContent = templateEngine.process("email-template/create_account", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail, fromName);
            helper.setTo(to);
            helper.setSubject("[JAF CINEMA] Thông tin tài khoản đăng nhập");
            helper.setText(htmlContent, true);

        mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send account creation email: " + e.getMessage());
        }
    }
}