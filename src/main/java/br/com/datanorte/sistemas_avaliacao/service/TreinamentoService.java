package br.com.datanorte.sistemas_avaliacao.service;

import br.com.datanorte.sistemas_avaliacao.controller.request.AvaliarSuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.request.TreinamentoRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.request.VincularSuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteResponseDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteTreinamentoResponseDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.TreinamentoResponseDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Suporte;
import br.com.datanorte.sistemas_avaliacao.entity.SuporteTreinamento;
import br.com.datanorte.sistemas_avaliacao.entity.Treinamento;
import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import br.com.datanorte.sistemas_avaliacao.exception.UserNotFoundException;
import br.com.datanorte.sistemas_avaliacao.mapper.SuporteMapper;
import br.com.datanorte.sistemas_avaliacao.mapper.TreinamentoMapper;
import br.com.datanorte.sistemas_avaliacao.repository.SuporteRepository;
import br.com.datanorte.sistemas_avaliacao.repository.SuporteTreinamentoRepository;
import br.com.datanorte.sistemas_avaliacao.repository.TreinamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreinamentoService {

    private final TreinamentoRepository treinamentoRepository;
    private final SuporteRepository suporteRepository;
    private final SuporteTreinamentoRepository vinculoRepository;
    private final TreinamentoMapper mapper;

    @Transactional
    public TreinamentoResponseDTO salvar(TreinamentoRequestDTO request) {
        Treinamento treinamento = mapper.toEntity(request);
        Treinamento treinamentoSalvo = treinamentoRepository.save(treinamento);
        return mapper.toResponse(treinamentoSalvo);
    }

    @Transactional
    public List<TreinamentoResponseDTO> listarTodos() {
        List<Treinamento> treinamentos = treinamentoRepository.findAll();
        return treinamentos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!treinamentoRepository.existsById(id)) {
            throw new EntityNotFoundException("Treinamento não encontrado");
        }
        treinamentoRepository.deleteById(id);
    }



    @Transactional
    public SuporteTreinamentoResponseDTO vincularSuporte(VincularSuporteRequestDTO request) {
        Suporte suporte = suporteRepository.findById(request.suporteId())
                .orElseThrow(() -> new EntityNotFoundException("Suporte não encontrado"));

        Treinamento treinamento = treinamentoRepository.findById(request.treinamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Treinamento não encontrado"));

        SuporteTreinamento vinculo = SuporteTreinamento.builder()
                .suporte(suporte)
                .treinamento(treinamento)
                .mesReferencia(request.mesReferencia()) // Salvando o mês vindo do DTO
                .build();

        return mapper.toSuporteTreinamentoResponse(vinculoRepository.save(vinculo));
    }

    @Transactional
    public SuporteTreinamentoResponseDTO atribuirNota(Long id, AvaliarSuporteRequestDTO request) {
        SuporteTreinamento vinculo = vinculoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vínculo não encontrado"));

        vinculo.setNota(request.nota());
        vinculo.setComentario(request.comentario());

        SuporteTreinamento avaliado = vinculoRepository.save(vinculo);
        return mapper.toSuporteTreinamentoResponse(avaliado);
    }
    @Transactional
    public TreinamentoResponseDTO findById(Long id) {
        Treinamento treinamento = treinamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Treinamento não encontrado"));

        return mapper.toResponse(treinamento);
    }


}
