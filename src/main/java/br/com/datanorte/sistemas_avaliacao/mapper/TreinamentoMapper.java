package br.com.datanorte.sistemas_avaliacao.mapper;

import br.com.datanorte.sistemas_avaliacao.controller.request.TreinamentoRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteTreinamentoResponseDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.TreinamentoResponseDTO;
import br.com.datanorte.sistemas_avaliacao.entity.SuporteTreinamento;
import br.com.datanorte.sistemas_avaliacao.entity.Treinamento;
import org.springframework.stereotype.Component;

@Component
public class TreinamentoMapper {

    /**
     * Converte o Request para a Entidade Treinamento (Criação)
     */
    public Treinamento toEntity(TreinamentoRequestDTO request) {
        return Treinamento.builder()
                .nome(request.nome())
                .descricao(request.descricao())
                .build();
    }

    /**
     * Converte a Entidade Treinamento para o Response DTO
     */
    public TreinamentoResponseDTO toResponse(Treinamento entity) {
        return new TreinamentoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getCreatedAt()
        );
    }

    /**
     * Converte a Entidade de Vínculo (Intermediária) para o Response DTO
     * Este mapeamento extrai os nomes das entidades relacionadas (Suporte e Treinamento)
     */
    public SuporteTreinamentoResponseDTO toSuporteTreinamentoResponse(SuporteTreinamento entity) {
        return new SuporteTreinamentoResponseDTO(
                entity.getId(),
                entity.getSuporte().getNome(),     // Pegando nome da entidade Suporte
                entity.getTreinamento().getNome(), // Pegando nome da entidade Treinamento
                entity.getNota(),
                entity.getComentario(),
                entity.getMesReferencia(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
