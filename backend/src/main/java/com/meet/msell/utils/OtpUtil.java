package com.meet.msell.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {

    public static String generateOtp() {
        int otpLength = 6;

        StringBuilder otp = new StringBuilder(otpLength);
        Random random = new Random();

        for (int i = 1; i <= 6; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

}
