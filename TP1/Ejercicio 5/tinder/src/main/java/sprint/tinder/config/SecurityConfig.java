package sprint.tinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
/*
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sprint.tinder.services.UsuarioServicio;
*/

// Esta clase es la versión moderna del video, ya que la que usa ahi está deprecada, entonces lo que él ponia en su Main ahora lo ponemos en esta clase y de otra forma
//

@Configuration
public class SecurityConfig {
    /*
    private final UsuarioServicio usuarioServicio;

    public SecurityConfig(UsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }
    */

    //Configuración de rutas y login (ahora permite todo)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll()   // permite TODO
                )
                .csrf(csrf -> csrf.disable()) // opcional, si da problemas con formularios
                .build();
    }

    /*
    //Password encoder disponible en el contexto
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //AuthenticationManager (reemplaza configureGlobal del video)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    //UserDetailsService (Spring usará tu UsuarioServicio para autenticar)
    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioServicio;
    }
    */

}
