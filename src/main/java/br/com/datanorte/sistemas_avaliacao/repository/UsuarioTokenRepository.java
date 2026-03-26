package br.com.datanorte.sistemas_avaliacao.repository;

import br.com.datanorte.sistemas_avaliacao.entity.UsuarioToken;
import br.com.datanorte.sistemas_avaliacao.enums.TokenTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioTokenRepository extends JpaRepository<UsuarioToken, Long> {

    Optional<UsuarioToken> findByTokenAndTipo(String token, TokenTipo tipo);

    void deleteByUsuarioIdAndTipo(Long usuarioId, TokenTipo tipo);
}