package com.gimnasio.gimnasio;


import com.gimnasio.gimnasio.security.CustomAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAuthenticationProvider customAuthenticationProvider) throws Exception {
        http
                .authenticationProvider(customAuthenticationProvider)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/views/login", "/css/**", "/js/**", "/img/**", "/static/**", "/bootstrap/**").permitAll()
                        .requestMatchers("/inicio").permitAll()
                        .requestMatchers("/views/admin/**").hasRole("ADMINISTRATIVO")
                        .requestMatchers("/views/profesor/**").hasRole("PROFESOR")
                        .requestMatchers("/views/socio/**").hasRole("SOCIO")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .usernameParameter("nombreUsuario")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/casa", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/logout")  // Permite GET /logout sin CSRF token
                );


        return http.build();
    }
}
