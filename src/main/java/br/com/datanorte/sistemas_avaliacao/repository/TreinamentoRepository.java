package br.com.datanorte.sistemas_avaliacao.repository;

import br.com.datanorte.sistemas_avaliacao.entity.Treinamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreinamentoRepository extends JpaRepository<Treinamento, Long> {
}
