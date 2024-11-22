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

            String content = String.format(
                    """
                            <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
                                <div style="background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); padding: 30px;">
                                    <div style="text-align: center; margin-bottom: 30px;">
                                        <h1 style="color: #333333; margin: 0; font-size: 24px;">Xác thực Email</h1>
                                    </div>
                                    <div style="background-color: #f8f9fa; border-radius: 5px; padding: 20px; text-align: center; margin: 20px 0;">
                                        <p style="color: #666666; font-size: 16px; margin-bottom: 10px;">Mã xác thực của bạn là:</p>
                                        <h2 style="color: #4CAF50; font-size: 32px; letter-spacing: 5px; margin: 0;">%s</h2>
                                    </div>  
                                    <p style="color: #dc3545; font-size: 14px; text-align: center; margin: 20px 0;">
                                        Mã này sẽ hết hạn trong 1 phút
                                    </p>

                                    <div style="border-top: 1px solid #eeeeee; margin-top: 30px; padding-top: 20px;">
                                        <p style="color: #999999; font-size: 12px; text-align: center; margin: 0;">
                                            Email này được gửi tự động, vui lòng không trả lời.<br>
                                            Nếu bạn không yêu cầu mã này, vui lòng bỏ qua email này.
                                        </p>
                                    </div>
                                </div>
                            </div>
                            """,
                    otp);
            helper.setText(content, true);
            mailSender.send(message);

        } catch (Exception e) {
            log.error("Lỗi khi gửi email: ", e);
            throw new RuntimeException("Không thể gửi email");
        }
    }
}