package br.com.datanorte.sistemas_avaliacao.controller.request;

public record UsuarioRequestDTO(Long id, String name,
                                String email,
                                String password,
                                Character status,
                                Character perfil) {
}
