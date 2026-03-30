package br.com.datanorte.sistemas_avaliacao.controller.response;

import java.time.LocalDateTime;

public record TreinamentoResponseDTO(
        Long id,
        String nome,
        String descricao,
        LocalDateTime createdAt
) {}