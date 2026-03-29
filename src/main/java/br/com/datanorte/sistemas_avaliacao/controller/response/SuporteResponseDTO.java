package br.com.datanorte.sistemas_avaliacao.controller.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SuporteResponseDTO (
    Long id,
    String nome,
    LocalDateTime dataCriacao,
    LocalDateTime dataEdicao
){}