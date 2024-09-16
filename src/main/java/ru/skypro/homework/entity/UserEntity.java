package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Сущность для представления пользователя в базе данных.
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = {"username"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    /**
     * Email пользователя.
     */
    @Column(name = "email")
    private String email;

    /**
     * Логин пользователя.
     */
    @Column(name = "username")
    private String username;

    /**
     * Пароль пользователя.
     */
    @Column(name = "password")
    private String password;

    /**
     * Имя пользователя.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Телефон пользователя.
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Роль пользователя.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    /**
     * Ссылка на аватар пользователя.
     */
    @Column(name = "image")
    private String image;

    /**
     * Флаг, указывающий, включен ли пользователь.
     */
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * Список объявлений, созданных пользователем.
     */
    @OneToMany(mappedBy = "user")
    private List<AdEntity> adsList;
}