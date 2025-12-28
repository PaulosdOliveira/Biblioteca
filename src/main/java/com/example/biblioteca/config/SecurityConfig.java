package com.example.biblioteca.config;


import com.example.biblioteca.application.jwt.JwtFilter;
import com.example.biblioteca.application.jwt.JwtService;
import com.example.biblioteca.application.usuario.UsuarioService;
import com.example.biblioteca.model.usuario.enums.Perfil;
import com.example.biblioteca.repository.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception{
        return  http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/usuario" ).permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/usuario/adm").hasRole(Perfil.ADM.name());
                    auth.requestMatchers("/usuario/validar").permitAll();
                    auth.requestMatchers("/usuario/solicitar-codigo").permitAll();
                    auth.requestMatchers("/usuario/login").permitAll();
                    auth.requestMatchers(HttpMethod.GET,"/cliente").permitAll();
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new
                GrantedAuthorityDefaults("");
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }
}
