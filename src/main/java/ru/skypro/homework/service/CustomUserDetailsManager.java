package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.AuthorityEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.AuthorityRepository;
import ru.skypro.homework.repository.UserEntityRepository;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsManager implements UserDetailsManager {
    private final UserEntityRepository userEntityRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserDetails user) {
        UserEntity userEntity = new UserEntity();
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
    public void updateUser(UserDetails user) {
        UserEntity userEntity = userEntityRepository.findByUsername(user.getUsername());
        if(Objects.isNull(userEntity)){
            throw new UsernameNotFoundException("User not found: " + user.getUsername());
        }
        userEntity.setPassword(user.getPassword());
        userEntity.setEnabled(user.isEnabled());
        userEntityRepository.save(userEntity);

        authorityRepository.deleteById(user.getUsername());
        user.getAuthorities().forEach(authority -> {
            AuthorityEntity authorityEntity = new AuthorityEntity();
            authorityEntity.setUsername(user.getUsername());
            authorityEntity.setAuthority(authority.getAuthority());
            authorityRepository.save(authorityEntity);
        });
    }

    @Override
    public void deleteUser(String username) {
        userEntityRepository.deleteByUsername(username);
        authorityRepository.deleteById(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        if(Objects.isNull(currentUser)){
            throw new AccessDeniedException("You do not have permission to change password");
        }

        String username = currentUser.getName();
        UserEntity userEntity = userEntityRepository.findByUsername(username);

        if(Objects.isNull(userEntity)){
            throw new UsernameNotFoundException("User not found: " + username);
        }

        if(!passwordEncoder.matches(oldPassword,userEntity.getPassword())){
            throw new BadCredentialsException("Old password is incorrect");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);

        userEntity.setPassword(encodedPassword);
        userEntityRepository.save(userEntity);
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
