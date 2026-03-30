package br.com.datanorte.sistemas_avaliacao.controller.request;

public record AvaliarSuporteRequestDTO(
        Integer nota,
        String comentario
) {}