package br.com.datanorte.sistemas_avaliacao.controller.response;

import lombok.Builder;

@Builder
public record UsuarioResponseDTO(Long id, String name,
                                 String email) {
}
