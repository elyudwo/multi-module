package com.example.core.user.service;

import com.example.core.user.domain.UserEntity;
import com.example.core.user.repository.UserRepository;
import io.kr.demo.infra.s3.ImageRepository;
import io.kr.demo.infra.s3.MultipartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class UserImageService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private static final String BASIC_PROFILE_IMAGE = "https://studyhub-s3.s3.ap-northeast-2.amazonaws.com/avatar_l%401x.png";

    public void uploadUserImage(Long userId, MultipartFile multipartFile) throws IOException {
        UserEntity user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        MultipartDto multipartDto = new MultipartDto(multipartFile.getName(), multipartFile.getSize(), multipartFile.getContentType(), multipartFile.getInputStream());

        user.getUserImage().updateImage(imageRepository.saveImage(multipartDto));
    }

    public void deleteUserImage(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        user.getUserImage().updateImage(BASIC_PROFILE_IMAGE);
    }
}
