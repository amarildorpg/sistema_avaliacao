package br.com.datanorte.sistemas_avaliacao.entity;

import br.com.datanorte.sistemas_avaliacao.enums.TokenTipo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenTipo tipo;

    @Column(nullable = false)
    private LocalDateTime expiracao;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public boolean isExpirado() {
        return LocalDateTime.now().isAfter(expiracao);
    }

    public static UsuarioToken gerar(Usuario usuario, TokenTipo tipo) {
        return UsuarioToken.builder()
                .token(UUID.randomUUID().toString())
                .usuario(usuario)
                .tipo(tipo)
                .expiracao(LocalDateTime.now().plusHours(2))
                .build();
    }
}