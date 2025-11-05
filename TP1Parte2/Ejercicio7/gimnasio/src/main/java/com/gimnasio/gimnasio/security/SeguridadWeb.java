package com.gimnasio.gimnasio.security;

import com.gimnasio.gimnasio.security.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SeguridadWeb {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            CustomAuthenticationProvider customAuthenticationProvider
    ) throws Exception {

        http
                .authenticationProvider(customAuthenticationProvider)
                .authorizeHttpRequests(auth -> auth
                        // Recursos estáticos públicos
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/img/**",
                                "/vendor/**",
                                "/fonts/**"
                        ).permitAll()
                        // páginas públicas
                        .requestMatchers("/", "/home", "/login", "/registro").permitAll()
                        // (ejemplo) zona admin
                        .requestMatchers("/admin/**").hasRole("ADMINISTRATIVO")
                        // todo lo demás requiere login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .loginProcessingUrl("/logincheck") // donde hace POST tu form
                        .usernameParameter("nombreUsuario") // name del input
                        .passwordParameter("password")      // name del input
                        .defaultSuccessUrl("/inicio", true) // a dónde va luego de loguear
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // para simplificar

        return http.build();
    }
}
