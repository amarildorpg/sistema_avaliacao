package br.com.datanorte.sistemas_avaliacao.controller;

import br.com.datanorte.sistemas_avaliacao.controller.request.SuporteRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.SuporteResponseDTO;
import br.com.datanorte.sistemas_avaliacao.service.SuporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
public class SuporteController {

    private final SuporteService service;

    @PostMapping("/save")
    public ResponseEntity<SuporteResponseDTO> save(@RequestBody SuporteRequestDTO dto) {
        return ResponseEntity.status(201).body(service.save(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SuporteResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuporteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuporteResponseDTO> updateById(@PathVariable Long id,
                                                        @RequestBody SuporteRequestDTO dto) {
        return ResponseEntity.ok(service.updateById(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}