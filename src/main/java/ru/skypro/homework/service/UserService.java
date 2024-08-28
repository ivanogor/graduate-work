package ru.skypro.homework.service;

import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.StatusDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    StatusDto setPassword(NewPasswordDto newPassword);
    User getCurrentUser();
    UpdateUserDto updateUser(UpdateUserDto updateUserDto);
    StatusDto updateImage(String imageLink);
}
