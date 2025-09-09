package com.cyboul.demo.config;

import com.cyboul.demo.web.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Access H2 console with spring security ON
 */
@Profile("h2")
@Configuration
@EnableWebSecurity
public class H2WebSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    private static final String[] DEFAULT_NAV = new String[]{ "/", "/api/auth/**" };
    private static final String[] ANGU_ASSETS = new String[]{ "/index.html", "/*.css", "/*.js" };
    private static final String[] H2_CONSOLE  = new String[]{ "/h2-console/**" };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers(DEFAULT_NAV).permitAll()
                        .requestMatchers(ANGU_ASSETS).permitAll()
                        .requestMatchers(H2_CONSOLE).permitAll()
                        .anyRequest().authenticated())

                // Stateless API, so...
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)

                // Disabled for dev purpose
                //.cors(cors -> {})   // Declare CorsConfigurationSource (bean) and do nothing with it
                .cors(AbstractHttpConfigurer::disable)

                // !!! Disable CSRF & allow iframes for H2 console
                //.csrf(csrf -> csrf.ignoringRequestMatchers(H2_CONSOLE))
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

                //.formLogin((form) -> form.loginPage("/login").permitAll())
                //.logout(LogoutConfigurer::permitAll);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // This bean will be used by `http.cors(cors -> {})` in `securityFilterChain()`
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}