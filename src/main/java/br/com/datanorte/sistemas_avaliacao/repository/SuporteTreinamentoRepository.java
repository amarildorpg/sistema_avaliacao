package br.com.datanorte.sistemas_avaliacao.repository;

import br.com.datanorte.sistemas_avaliacao.entity.SuporteTreinamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuporteTreinamentoRepository extends JpaRepository<SuporteTreinamento, Long> {

    // Busca todos os treinamentos de um suporte específico
    List<SuporteTreinamento> findBySuporteId(Long suporteId);

    // Busca todos os suportes vinculados a um treinamento específico
    List<SuporteTreinamento> findByTreinamentoId(Long treinamentoId);
}
