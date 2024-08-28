package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.StatusDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public StatusDto setPassword(NewPasswordDto newPassword) {
        return StatusDto.builder().build();
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
    public StatusDto updateImage(String imageLink) {
        return StatusDto.builder().build();
    }
}
