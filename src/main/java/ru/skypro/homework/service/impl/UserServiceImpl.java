package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.AuthorityEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AuthorityRepository;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.UserService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserEntityRepository userEntityRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void setPassword(NewPassword newPassword) {

    }

    @Override
    public User getCurrentUser() {
        return null;
    }
}
