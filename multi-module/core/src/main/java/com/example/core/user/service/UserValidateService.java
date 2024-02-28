package com.example.core.user.service;

import com.example.core.user.domain.UserEntity;
import com.example.core.user.dto.data.SignInData;
import com.example.core.user.repository.UserRepository;
import com.example.core.user.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.core.user.util.PasswordEncoder.encode;
import static com.example.core.user.util.PasswordEncoder.matches;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserValidateService {

    private final UserRepository userRepository;

    public void validateExistUserEmail(String email) {
        if (userRepository.existsByUserInfo_Email(email)) {
            throw new RuntimeException();
        }
    }

    public void verifyPassword(Long userId, String password) {
        UserEntity user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        if (!matches(encode(user.getUserInfo().getEmail(), password), user.getUserInfo().getPassword())) {
            throw new RuntimeException();
        }
    }
}
