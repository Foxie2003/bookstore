package com.bnpstudio.bookstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Random;

@Service
@Slf4j
public class OtpService {
    
    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    @SuppressWarnings("null")
    public void generateAndSendOtp(String email) {
        try {
            if (!redisTemplate.getConnectionFactory().getConnection().isClosed()) {
                String otp = generateOtp();
                log.debug("Đang tạo OTP cho email: {}", email);
                
                redisTemplate.opsForValue().set(
                    email,
                    otp,
                    Duration.ofMinutes(1)
                );
                
                String storedOtp = redisTemplate.opsForValue().get(email);
                log.debug("Đã lưu OTP vào Redis: {}", storedOtp);
                
                emailService.sendOtpEmail(email, otp);
            } else {
                throw new RuntimeException("Không thể kết nối tới Redis server");
            }
        } catch (Exception e) {
            log.error("Lỗi khi tạo/gửi OTP: ", e);
            throw new RuntimeException("Không thể gửi mã OTP. Vui lòng thử lại sau.");
        }
    }
    
    public boolean validateOtp(String email, String otp) {
        try {
            String storedOtp = redisTemplate.opsForValue().get(email);
            log.debug("Validating OTP for email: {}. Stored OTP: {}", email, storedOtp);
            
            if (storedOtp == null) {
                return false;
            }
            
            boolean isValid = storedOtp.equals(otp);
            
            if (isValid) {
                redisTemplate.delete(email);
            }
            
            return isValid;
            
        } catch (Exception e) {
            log.error("Error while validating OTP: ", e);
            return false;
        }
    }
    
    private String generateOtp() {
        Random random = new Random();
        int number = random.nextInt(999999);
        return String.format("%06d", number);
    }
} 