package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.entity.User;

public interface UserService {
    void setPassword(NewPasswordDto newPassword);
    User getCurrentUser();
    UpdateUserDto updateUser(UpdateUserDto updateUserDto);
    void updateImage(MultipartFile image);
}
