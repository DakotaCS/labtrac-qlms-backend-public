package com.quantus.backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.util.Map;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-11-26
 */

@Service
@Transactional
@RequiredArgsConstructor
public class MailSenderHelper {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     *
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendSimpleEmail(
            String toEmail,
            String subject,
            String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("donotreply@dkstechnicalsolutions.ca");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("Mail Sent successfully...");
    }

    /**
     * Generates an Email
     *
     * @param recipient
     * @param subject
     * @param embeddedEmailVariables
     * @param template
     */
    @Async
    public void sendEmail(
            final String recipient, String subject, Map<String,Object> embeddedEmailVariables, String template) {
        Context context = new Context();
        context.setVariables(embeddedEmailVariables);
        String htmlTemplate = templateEngine.process(template, context);
        MimeMessagePreparator mimeMP = mimeMessage -> {
            MimeMessageHelper mh = new MimeMessageHelper(mimeMessage);
            mh.setFrom("donotreply@dkstechnicalsolutions.ca");
            mh.addTo(recipient);
            mh.setSubject(subject);
            mh.setText(htmlTemplate, true);
        };
        try {
            mailSender.send(mimeMP);
        } catch (Exception ex) {
            throw new CustomExceptionHandler.BadRequestCustomException("Email could not be sent");
        }
    }
}
