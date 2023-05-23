package com.example.signupandemailauth.service;

import com.example.signupandemailauth.dto.UserDTO;
import com.example.signupandemailauth.entity.User;

public interface UserService {
    User create(UserDTO userDTO);
}
