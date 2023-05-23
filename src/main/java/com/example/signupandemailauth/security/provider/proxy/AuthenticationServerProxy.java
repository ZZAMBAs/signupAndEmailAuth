package com.example.signupandemailauth.security.provider.proxy;

import com.example.signupandemailauth.entity.Otp;
import com.example.signupandemailauth.entity.User;
import com.example.signupandemailauth.entity.status.UserStatus;
import com.example.signupandemailauth.repository.OtpRepository;
import com.example.signupandemailauth.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationServerProxy {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;

    public void checkOTP(String name, String code){
        Otp otp = otpRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("There is no otp about " + name + "."));

        User user = userRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("No user about " + name + "."));

        if (!otp.getCode().equals(code))
            throw new BadCredentialsException("Code doesn't equals OTP.");

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public boolean checkUser(String name, String password){
        User user = userRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
        boolean isSamePassword = passwordEncoder.matches(password, user.getPassword());
        return isSamePassword && user.getStatus() == UserStatus.ACTIVE;
    }
}
