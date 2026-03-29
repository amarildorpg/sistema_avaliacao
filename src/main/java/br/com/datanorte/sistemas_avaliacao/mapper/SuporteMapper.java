package br.com.datanorte.sistemas_avaliacao.mapper;

import br.com.datanorte.sistemas_avaliacao.controller.request.SuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteResponseDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Suporte;

public class SuporteMapper {

    public static Suporte toEntity(SuporteRequestDTO dto) {
        return Suporte.builder()
                .nome(dto.nome())
                .build();
    }

    public static SuporteResponseDTO toDTO(Suporte entity) {
        return SuporteResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .dataCriacao(entity.getDataCriacao())
                .dataEdicao(entity.getDataEdicao())
                .build();
    }
}
