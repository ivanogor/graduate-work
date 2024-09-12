package ru.skypro.homework.service;

import org.springframework.http.ResponseEntity;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;

public interface UserService {
    void setPassword(NewPassword newPassword);

    User getCurrentUser();
}
