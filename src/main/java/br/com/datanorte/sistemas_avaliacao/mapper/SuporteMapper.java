package br.com.datanorte.sistemas_avaliacao.mapper;

import br.com.datanorte.sistemas_avaliacao.controller.request.SuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteResponseDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteTreinamentoResponseDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Suporte;

import java.util.List;
import java.util.stream.Collectors;

public class SuporteMapper {

    public static Suporte toEntity(SuporteRequestDTO dto) {
        return Suporte.builder()
                .nome(dto.nome())
                .build();
    }

    public static SuporteResponseDTO toDTO(Suporte entity) {
        // Mapeamos a lista de treinamentos da entidade para DTOs
        List<SuporteTreinamentoResponseDTO> treinamentosDTO = entity.getTreinamentos() == null
                ? List.of()
                : entity.getTreinamentos().stream()
                  .map(t -> new SuporteTreinamentoResponseDTO(
                          t.getId(),
                          t.getSuporte().getNome(),
                          t.getTreinamento().getNome(),
                          t.getNota(),
                          t.getComentario(),
                          t.getMesReferencia(),
                          t.getCreatedAt(),
                          t.getUpdatedAt()
                  ))
                  .collect(Collectors.toList());

        return SuporteResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .dataCriacao(entity.getDataCriacao())
                .dataEdicao(entity.getDataEdicao())
                .treinamentos(treinamentosDTO) // Passando a lista populada
                .build();
    }
}
