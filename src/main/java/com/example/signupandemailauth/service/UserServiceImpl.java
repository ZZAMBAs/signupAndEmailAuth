package com.example.signupandemailauth.service;

import com.example.signupandemailauth.dto.UserDTO;
import com.example.signupandemailauth.entity.Otp;
import com.example.signupandemailauth.entity.User;
import com.example.signupandemailauth.entity.status.UserStatus;
import com.example.signupandemailauth.repository.OtpRepository;
import com.example.signupandemailauth.repository.UserRepository;
import com.example.signupandemailauth.util.GenerateUtils;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public User create(UserDTO userDTO) {
        User user = createUser(userDTO);

        if (user.getStatus() == UserStatus.ACTIVE)
            throw new EntityExistsException("The user already exists.");

        Otp otp = setOTP(user);

        emailService.sendEmailOTP(otp.getName(), otp.getCode());

        return user;
    }

    private Otp setOTP(User user){
        Otp otp = otpRepository.findByName(user.getName()).orElseGet(() -> Otp.builder().name(user.getName()).build());
        String newOtp = GenerateUtils.generateOTP();
        otp.setCode(newOtp);
        return otpRepository.save(otp);
    }

    private User createUser(UserDTO userDTO) {
        Optional<User> foundUser = userRepository.findByName(userDTO.name());
        User user = foundUser.orElseGet(() -> {
            User createdUser = User.builder()
                    .name(userDTO.name())
                    .password(passwordEncoder.encode(userDTO.password()))
                    .status(UserStatus.INACTIVE)
                    .build();
            return userRepository.save(createdUser);
            }
        );
        return user;
    }
}
