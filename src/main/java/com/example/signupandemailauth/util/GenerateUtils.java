package com.example.signupandemailauth.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class GenerateUtils {
    public static String generateOTP() {
        SecureRandom secureRandom;
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Exception making random number.", e);
        }
        int randomInt = secureRandom.nextInt(1000000);

        return String.format("%06d", randomInt);
    }
}
