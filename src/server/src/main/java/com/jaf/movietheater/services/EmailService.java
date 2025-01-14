package com.jaf.movietheater.services;

import java.io.File;

public interface EmailService {
    void sendSimpleEmail(String to, String subject, String text);

    void sendHtmlEmail(String to, String subject, String htmlContent);

    void sendHtmlEmail(String to, String subject, String htmlContent, File attachment);

    void sendAccountCreationEmail(String to, String username, String password);
}
