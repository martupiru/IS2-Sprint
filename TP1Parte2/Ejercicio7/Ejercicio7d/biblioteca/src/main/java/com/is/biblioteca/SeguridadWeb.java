package com.is.biblioteca;

import com.is.biblioteca.business.logic.service.CustomOAuth2UserService;
import com.is.biblioteca.business.logic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SeguridadWeb {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/admin/*").hasRole("ADMIN")
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/**", "/registro", "/usuario/login", "/usuario/inicio", "/inicio").permitAll()
                        .anyRequest().authenticated()
                )
                // Login tradicional
                .formLogin(form -> form
                        .loginPage("/usuario/login")
                        .loginProcessingUrl("/usuario/inicio")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio", true)
                        .permitAll()
                )
                // OAuth2 Login (Google)
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/usuario/login") // Misma página de login
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .defaultSuccessUrl("/inicio", true)
                        .failureUrl("/usuario/login?error=oauth")
                )
                .logout(logout -> logout
                        .logoutUrl("/usuario/logout")
                        .logoutSuccessUrl("/usuario/login")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}