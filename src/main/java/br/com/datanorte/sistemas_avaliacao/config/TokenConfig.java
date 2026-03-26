package br.com.datanorte.sistemas_avaliacao.config;

import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(usuario.getEmail())
                .withClaim("userID", usuario.getId())
                .withClaim("name", usuario.getName())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuer("sistemas_avaliacao")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return Optional.of(JWTUserData
                    .builder()
                    .id(decodedJWT.getClaim("userID").asLong())
                    .name(decodedJWT.getClaim("name").asString())
                    .email(decodedJWT.getSubject())
                    .build());
        } catch (JWTVerificationException exception) {
            return Optional.empty();
        }
    }
}
