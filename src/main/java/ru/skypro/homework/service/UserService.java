package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.entity.UserEntity;

public interface UserService {
    void setPassword(NewPassword newPassword);
    UserEntity getCurrentUser();
    UpdateUser updateUser(UpdateUser updateUser);
    void updateImage(MultipartFile image);
}
