package br.com.datanorte.sistemas_avaliacao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "suporte")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Suporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "updated_at")
    private LocalDateTime dataEdicao;

    @OneToMany(mappedBy = "suporte", fetch = FetchType.LAZY)
    private List<SuporteTreinamento> treinamentos;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataEdicao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataEdicao = LocalDateTime.now();
    }
}
