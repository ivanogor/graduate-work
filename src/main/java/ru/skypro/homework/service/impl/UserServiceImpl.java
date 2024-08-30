package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void setPassword(NewPasswordDto newPassword) {

    }

    @Override
    public User getCurrentUser() {
        return User.builder().build();
    }

    @Override
    public UpdateUserDto updateUser(UpdateUserDto updateUserDto) {
        return UpdateUserDto.builder().build();
    }

    @Override
    public void updateImage(MultipartFile image) {

    }
}
