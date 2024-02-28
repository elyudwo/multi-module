package com.example.core.user.service;

import com.example.core.user.domain.UserEntity;
import com.example.core.user.dto.data.UpdateMajorData;
import com.example.core.user.dto.data.UpdateNicknameData;
import com.example.core.user.dto.data.UpdatePasswordData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class UserUpdateService {

    private final UserFindService userFindService;

    public void updateNickname(UpdateNicknameData data) {
        UserEntity user = userFindService.getUserById(data.getUserId());
        user.getUserInfo().updateNickname(data.getNickname());
    }

    public void updateMajor(UpdateMajorData data) {
        UserEntity user = userFindService.getUserById(data.getUserId());
        user.getUserTypeInfo().updateMajor(data.getMajor());
    }

    public void updatePassword(UpdatePasswordData data) {
        UserEntity user = userFindService.getUserByEmail(data.getEmail());
        user.getUserInfo().updatePassword(data);
    }
}
