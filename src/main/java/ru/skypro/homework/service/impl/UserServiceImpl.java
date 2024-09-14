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
import ru.skypro.homework.repository.AuthorityRepository;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.UserService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void setPassword(NewPassword newPassword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userEntityRepository.findByUsername(username);

        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }

        if(!passwordEncoder.matches(newPassword.getCurrentPassword(), user.getPassword())) {
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

        if(Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User not found");
        }

        return User.toDto(userEntity);
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userEntityRepository.findByUsername(username);

        if(Objects.isNull(userEntity)) {
            throw new UsernameNotFoundException("User not found");
        }

        userEntity.setFirstName(updateUser.getFirstName());
        userEntity.setLastName(updateUser.getLastName());
        userEntity.setPhone(updateUser.getPhone());

        return UpdateUser.toDto(userEntity);
    }

    @Override
    public void updateUserAvatar(MultipartFile image) {

    }

    @Override
    public boolean hasAccessToChangePassword(String username) {
        return userEntityRepository.findByUsername(username).isEnabled();
    }
}
