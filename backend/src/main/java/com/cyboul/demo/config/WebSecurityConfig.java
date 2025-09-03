package com.cyboul.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Profile("!h2")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/", "/home", "/api/**", "/assets/**", "/*.js", "/*.css", "/index.html").permitAll()
                    .anyRequest().authenticated())
            //.formLogin((form) -> form.loginPage("/login").permitAll())
            .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

}