package br.com.datanorte.sistemas_avaliacao.config;

import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import br.com.datanorte.sistemas_avaliacao.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenConfig tokenConfig;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (Strings.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {

            String token = authorizationHeader.substring("Bearer ".length());

            Optional<JWTUserData> optionalJWTUserData = tokenConfig.verifyToken(token);

            if (optionalJWTUserData.isPresent()) {

                JWTUserData jwtUserData = optionalJWTUserData.get();

                // 🔥 BUSCAR USUÁRIO NO BANCO
                Usuario usuario = usuarioRepository
                        .findByEmail(jwtUserData.email())
                        .orElse(null);

                // 🚨 VALIDA STATUS
                if (usuario == null || !usuario.isEnabled()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Usuário inativo");
                    return;
                }

                // ✅ AUTENTICA
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                usuario.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }

}
