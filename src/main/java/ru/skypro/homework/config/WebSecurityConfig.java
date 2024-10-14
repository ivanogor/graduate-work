package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Конфигурация безопасности веб-приложения.
 * Этот класс настраивает Spring Security для управления доступом к различным ресурсам приложения.
 * Он определяет белый список URL-адресов, которые доступны без аутентификации,
 * а также настраивает кодировщик паролей для безопасного хранения паролей пользователей.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Список URL-адресов, которые доступны без аутентификации.
     * Эти ресурсы включают в себя документацию Swagger, страницы входа и регистрации,
     * а также некоторые статические ресурсы.
     */
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
            "/ads",
            "/src/**"
    };

    /**
     * Создает и настраивает цепочку фильтров безопасности.
     * Этот метод определяет, какие запросы должны быть аутентифицированы,
     * а какие могут быть доступны без аутентификации.
     *
     * @param http Объект HttpSecurity, используемый для настройки безопасности.
     * @return Настроенная цепочка фильтров безопасности.
     * @throws Exception Если возникает ошибка при настройке безопасности.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .mvcMatchers("/ads/**", "/users/**")
                                        .authenticated())
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    /**
     * Создает и настраивает кодировщик паролей.
     * Этот метод использует BCryptPasswordEncoder для безопасного хранения паролей пользователей.
     *
     * @return Настроенный кодировщик паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}