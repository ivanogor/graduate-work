package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.AuthorityEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AuthorityRepository;
import ru.skypro.homework.repository.UserEntityRepository;
import ru.skypro.homework.service.CustomUserDetailsManager;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsManagerImpl implements CustomUserDetailsManager {
    private final UserEntityRepository userEntityRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void createUser(UserDetails user, Register register) {
        UserEntity userEntity = register.toUserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setEnabled(user.isEnabled());
        userEntityRepository.save(userEntity);

        user.getAuthorities().forEach(authority -> {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setUsername(user.getUsername());
            authorityEntity.setAuthority(authority.getAuthority());
            authorityRepository.save(authorityEntity);
        });
    }

    @Override
    public boolean userExists(String username) {
        return userEntityRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByUsername(username);
        if(Objects.isNull(userEntity)){
            throw new UsernameNotFoundException("User not found: " + username);

        }

        List<AuthorityEntity>  authorityEntities = authorityRepository.findAllById(Collections.singleton(username));
        List<GrantedAuthority> authorities = authorityEntities.stream()
                .map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getAuthority()))
                .collect(Collectors.toList());

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities(authorities)
                .build();
    }
}
