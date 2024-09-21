package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.utils.ImageMapper;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userEntityRepository;
    private final ImageMapper imageMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void setPassword(NewPassword newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userEntityRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!newPassword.getCurrentPassword().equals(user.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword.getNewPassword());

        user.setPassword(encodedNewPassword);
        userEntityRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userEntityRepository.findByUsername(username);

        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.toDto(userEntity);
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userEntityRepository.findByUsername(username);

        if (Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User not found");
        }

        userEntity.setFirstName(updateUser.getFirstName());
        userEntity.setLastName(updateUser.getLastName());
        userEntity.setPhone(updateUser.getPhone());

        return UpdateUser.toDto(userEntity);
    }

    @Override
    public void updateUserAvatar(MultipartFile image) {
        // Получаем текущего пользователя (например, из SecurityContext)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userEntityRepository.findByUsername(username);

        // Генерируем уникальный идентификатор для файла
        String uniqueId = UUID.randomUUID().toString();

        try {
            // Сохраняем файл и получаем путь к сохраненному файлу
            String imagePath = imageMapper.mapFileToPath(image, uniqueId);

            // Обновляем путь к аватару пользователя
            currentUser.setImage(imagePath);

            // Сохраняем изменения в базе данных
            userEntityRepository.save(currentUser);
        } catch (IOException e) {
            // Обработка ошибки, если не удалось сохранить файл
            throw new RuntimeException("Failed to update user avatar", e);
        }
    }

    @Override
    public boolean hasAccessToChangePassword(String username) {
        return userEntityRepository.findByUsername(username).isEnabled();
    }
}
