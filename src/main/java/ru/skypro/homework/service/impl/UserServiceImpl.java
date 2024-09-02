package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void setPassword(NewPassword newPassword) {

    }

    @Override
    public UserEntity getCurrentUser() {
        return UserEntity.builder().build();
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        return UpdateUser.builder().build();
    }

    @Override
    public void updateImage(MultipartFile image) {

    }
}
