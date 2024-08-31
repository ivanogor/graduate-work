package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UpdateUserDto;
import ru.skypro.homework.service.UserService;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/set_password")
    ResponseEntity<?> setPassword(@RequestBody NewPasswordDto newPassword){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/me")
    ResponseEntity<?> getCurrentUser(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/me")
    ResponseEntity<?> updateUser(UpdateUserDto updateUserDto){
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping(value = "/me/image", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserAvatar(@RequestParam("image") MultipartFile image) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
