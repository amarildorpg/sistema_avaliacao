package br.com.datanorte.sistemas_avaliacao.controller.response;

import java.time.LocalDateTime;

public record SuporteTreinamentoResponseDTO(
        Long id,
        String nomeSuporte,
        String nomeTreinamento,
        Integer nota,
        String comentario,
        String mesReferencia,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
