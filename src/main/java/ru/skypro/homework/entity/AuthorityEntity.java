package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Сущность для представления прав доступа пользователя в базе данных.
 */
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityEntity {

    /**
     * Имя пользователя (логин).
     */
    @Id
    private String username;

    /**
     * Права доступа пользователя.
     */
    private String authority;
}