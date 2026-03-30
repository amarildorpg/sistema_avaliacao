package br.com.datanorte.sistemas_avaliacao.controller.request;

public record VincularSuporteRequestDTO(
        Long suporteId,
        Long treinamentoId,
        String mesReferencia
) {}