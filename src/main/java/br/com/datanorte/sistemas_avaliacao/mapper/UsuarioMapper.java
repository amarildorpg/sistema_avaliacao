package br.com.datanorte.sistemas_avaliacao.mapper;

import br.com.datanorte.sistemas_avaliacao.controller.request.UsuarioRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.UsuarioResponseDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {
    public static Usuario toEntity(UsuarioRequestDTO dto) {
        return Usuario.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .perfil(dto.perfil())
                .status('0')
                .build();
    }

    public static UsuarioResponseDTO toResponse(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .name(usuario.getName())
                .email(usuario.getEmail())
                .build();
    }
}
