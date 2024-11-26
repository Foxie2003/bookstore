package com.bnpstudio.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("algoaitutor@gmail.com");
            helper.setTo(email);
            helper.setSubject("Mã xác nhận OTP");

            // Đọc template từ file
            String template = new String(getClass().getResourceAsStream("/templates/email/otp-template.html").readAllBytes());
            String content = String.format(template, otp);
            
            helper.setText(content, true);
            mailSender.send(message);

        } catch (Exception e) {
            log.error("Lỗi khi gửi email: ", e);
            throw new RuntimeException("Không thể gửi email");
        }
    }
}