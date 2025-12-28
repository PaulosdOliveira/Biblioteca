package com.example.biblioteca.application.jwt;

import com.example.biblioteca.application.usuario.UsuarioService;
import com.example.biblioteca.model.usuario.Usuario;
import com.example.biblioteca.model.usuario.MyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenByRequest(request);
        if (token != null) {
            String email = jwtService.getEmailByToken(token);
            Usuario usuario = usuarioService.findByEmail(email);
            UserDetails userDetails = new MyUserDetails(usuario);
            UsernamePasswordAuthenticationToken useAuth =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(useAuth);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        var metodo = request.getMethod();
        return path.contains("/login") || path.contains("/validar") ||
               (path.contains("/cliente") && metodo.equals(HttpMethod.GET.name()))
               || path.contains("/solicitar-codigo") ||
               (path.contains("/usuario") && metodo.equals(HttpMethod.POST.name()) && !path.contains("/adm"));
    }

    private String getTokenByRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            var tokenDividido = token.split(" ");
            return tokenDividido[1];
        }
        return null;
    }


}
