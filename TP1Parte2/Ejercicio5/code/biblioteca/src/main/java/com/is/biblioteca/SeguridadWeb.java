package com.is.biblioteca;

import com.is.biblioteca.business.logic.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // reemplaza a EnableGlobalMethodSecurity
// Ya no se hereda de WebSecurityConfigurerAdapter como en el video por versiones
public class SeguridadWeb {

    @Autowired
    private UsuarioService usuarioService;

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
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/**", "/registro", "/usuario/login", "usuario/inicio", "/inicio").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/usuario/login")
                        .loginProcessingUrl("/usuario/inicio")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio")
                        .permitAll()
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
