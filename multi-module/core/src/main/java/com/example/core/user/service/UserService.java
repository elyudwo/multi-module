package com.example.core.user.service;

import com.example.core.user.domain.UserActivityFinder;
import com.example.core.user.domain.UserEntity;
import com.example.core.user.dto.data.JwtData;
import com.example.core.user.dto.data.SignInData;
import com.example.core.user.dto.data.SignUpData;
import com.example.core.user.dto.data.UserActivityCountData;
import com.example.core.user.dto.data.UserDetailData;
import com.example.core.user.repository.UserRepository;
import io.kr.demo.infra.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private static final String BASIC_PROFILE_IMAGE = "https://studyhub-s3.s3.ap-northeast-2.amazonaws.com/avatar_l%401x.png";
    private final UserRepository userRepository;
    private final UserFindService userFindService;
    private final UserValidateService userValidateService;
    private final UserActivityFinder userActivityFinder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void registerUser(SignUpData signUpData) {
        userValidateService.validateExistUserEmail(signUpData.getEmail());
        userRepository.save(signUpData.toEntity(BASIC_PROFILE_IMAGE));
    }

    public JwtData loginUser(SignInData signInData) {
        UserEntity userEntity = userFindService.getUserByEmail(signInData.getEmail());
        userEntity.getUserInfo().userVerify(signInData);

        return new JwtData(jwtProvider.createJwtResponseDto(userEntity.getId()));
    }

    @Transactional
    public UserDetailData getUser(Long userId) {
        UserEntity user = userFindService.getUserById(userId);
        UserActivityCountData data = userActivityFinder.countUserActivity(userId);
        return new UserDetailData(user, data);
    }

    @Transactional
    public void deleteUser(Long userId) {
        UserEntity user = userFindService.getUserById(userId);
        userRepository.delete(user);
    }
}
