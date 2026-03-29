package br.com.datanorte.sistemas_avaliacao.service;

import br.com.datanorte.sistemas_avaliacao.controller.request.SuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteResponseDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Suporte;
import br.com.datanorte.sistemas_avaliacao.exception.UserNotFoundException;
import br.com.datanorte.sistemas_avaliacao.mapper.SuporteMapper;
import br.com.datanorte.sistemas_avaliacao.repository.SuporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuporteService {
    private final SuporteRepository repository;

    public SuporteResponseDTO save(SuporteRequestDTO dto) {
        Suporte suporte = SuporteMapper.toEntity(dto);
        Suporte saved = repository.save(suporte);
        return SuporteMapper.toDTO(saved);
    }

    public List<SuporteResponseDTO> findAll(){
        return repository.findAll()
                .stream()
                .map(SuporteMapper::toDTO)
                .toList();
    }

    public SuporteResponseDTO findById(Long id){
        Suporte suporte = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return SuporteMapper.toDTO(suporte);
    }

    public SuporteResponseDTO updateById(Long id, SuporteRequestDTO dto) {
        Suporte suporte = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        suporte.setNome(dto.nome());
        Suporte saved = repository.save(suporte);
        return SuporteMapper.toDTO(saved);
    }

    public void delete(Long id){
        if (!repository.existsById(id)){
            throw  new UserNotFoundException();
        }
        repository.deleteById(id);
    }

}
