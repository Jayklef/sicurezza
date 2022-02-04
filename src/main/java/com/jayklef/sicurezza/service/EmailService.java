package com.jayklef.sicurezza.service;

import com.jayklef.sicurezza.repository.EmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService implements EmailSender {

   @Autowired
    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String email) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Please confirm your email");
            helper.setFrom("jerry@woodcoreapp.com");
            mailSender.send(mimeMessage);
        }catch (MessagingException ex){
            log.error("failed to send email", ex);
            throw new IllegalStateException("failed to send email");
        }

    }
}
