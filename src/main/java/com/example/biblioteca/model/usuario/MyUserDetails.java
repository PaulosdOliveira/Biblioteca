package com.example.biblioteca.model.usuario;

import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MyUserDetails implements UserDetails {

    @Getter
    private final UUID id;

    private final String email;

    @Getter
    private final String nome;

    private final Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.nome = usuario.getNome();
        this.authorities = List.of(
                new SimpleGrantedAuthority(usuario.getPerfil().name())
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
