package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.StatusDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.service.UserService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/set_password")
    StatusDto setPassword(@RequestBody NewPasswordDto newPassword){
        return userService.setPassword(newPassword);
    }

    @GetMapping("/me")
    User getCurrentUser(){
        return userService.getCurrentUser();
    }

    @PatchMapping("/me")
    UpdateUserDto updateUser(UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }

    @PatchMapping("/me/image")
    StatusDto updateImage(String imageLink){
        return userService.updateImage(imageLink);
    }
}
