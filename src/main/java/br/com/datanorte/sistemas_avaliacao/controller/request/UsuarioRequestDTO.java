package br.com.datanorte.sistemas_avaliacao.controller.request;

import br.com.datanorte.sistemas_avaliacao.enums.Perfil;
import br.com.datanorte.sistemas_avaliacao.enums.Status;

public record UsuarioRequestDTO(Long id, String name,
                                String email,
                                String password,
                                Status status,
                                Perfil perfil) {
}
