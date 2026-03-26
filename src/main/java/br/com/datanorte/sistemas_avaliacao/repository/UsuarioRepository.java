package br.com.datanorte.sistemas_avaliacao.repository;

import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

}
