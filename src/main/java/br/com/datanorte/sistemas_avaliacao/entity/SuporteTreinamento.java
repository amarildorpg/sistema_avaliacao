package br.com.datanorte.sistemas_avaliacao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "suporte_treinamento")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuporteTreinamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "suporte_id", nullable = false)
    private Suporte suporte;

    // Permitimos null para que o suporte seja anexado antes de ter uma nota
    @Column(nullable = true)
    private Integer nota;

    @ManyToOne
    @JoinColumn(name = "treinamento_id", nullable = false)
    private Treinamento treinamento;
    private String comentario;
    @Column(name = "mes_referencia", nullable = false)
    private String mesReferencia;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
