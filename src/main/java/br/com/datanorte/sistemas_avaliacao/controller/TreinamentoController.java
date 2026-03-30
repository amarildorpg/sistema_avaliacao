package br.com.datanorte.sistemas_avaliacao.controller;

import br.com.datanorte.sistemas_avaliacao.controller.request.AvaliarSuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.request.TreinamentoRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.request.VincularSuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteResponseDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteTreinamentoResponseDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.TreinamentoResponseDTO;
import br.com.datanorte.sistemas_avaliacao.service.TreinamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treinamentos")
@RequiredArgsConstructor
public class TreinamentoController {

    private final TreinamentoService service;

    @PostMapping("/save")
    public ResponseEntity<TreinamentoResponseDTO> save(@RequestBody TreinamentoRequestDTO dto) {
        TreinamentoResponseDTO response = service.salvar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TreinamentoResponseDTO>> findAll() {
        List<TreinamentoResponseDTO> response = service.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreinamentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * ETAPA 1: Vincular o suporte ao treinamento com o mês de referência (Ex: "01/2026")
     */
    @PostMapping("/vincular")
    public ResponseEntity<SuporteTreinamentoResponseDTO> vincular(@RequestBody VincularSuporteRequestDTO dto) {
        SuporteTreinamentoResponseDTO response = service.vincularSuporte(dto);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * ETAPA 2: Atribuir a nota e comentário ao vínculo mensal existente
     */
    @PatchMapping("/avaliar/{id}")
    public ResponseEntity<SuporteTreinamentoResponseDTO> avaliar(
            @PathVariable Long id,
            @RequestBody AvaliarSuporteRequestDTO dto) {

        SuporteTreinamentoResponseDTO response = service.atribuirNota(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}