package br.com.datanorte.sistemas_avaliacao.controller;

import br.com.datanorte.sistemas_avaliacao.config.TokenConfig;
import br.com.datanorte.sistemas_avaliacao.controller.request.LoginRequest;
import br.com.datanorte.sistemas_avaliacao.controller.request.UsuarioRequestDTO;
import br.com.datanorte.sistemas_avaliacao.controller.response.LoginResponse;
import br.com.datanorte.sistemas_avaliacao.controller.response.UsuarioResponseDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import br.com.datanorte.sistemas_avaliacao.exception.UserInactiveException;
import br.com.datanorte.sistemas_avaliacao.exception.UsernameOrPasswordInvalidException;
import br.com.datanorte.sistemas_avaliacao.mapper.UsuarioMapper;
import br.com.datanorte.sistemas_avaliacao.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/datanorte/users")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioservice;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenService;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> save(@Valid @RequestBody UsuarioRequestDTO request) {
        Usuario savedUser = usuarioservice.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(UsuarioMapper.toResponse(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(),
                    request.password());
            Authentication authentication = authenticationManager.authenticate(userAndPass);
            Object principal = authentication.getPrincipal();

            if (!(principal instanceof Usuario user)) {
                throw new UsernameOrPasswordInvalidException("Usuário ou senha inválidos.");
            }
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponse(token));
        } catch (DisabledException e) {
            throw new UserInactiveException("Usuário inativo.");
        } catch (BadCredentialsException e) {
            throw new UsernameOrPasswordInvalidException("Usuário ou senha inválido.");
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioservice.findAll()
                .stream()
                .map(UsuarioMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return usuarioservice.findById(id)
                .map(usuario -> ResponseEntity.ok(UsuarioMapper.toResponse(usuario)))
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@Valid @RequestBody UsuarioRequestDTO dto, @PathVariable Long id) {
        return usuarioservice.update(id,
                        dto)
                .map(usuario -> ResponseEntity.ok(UsuarioMapper.toResponse(usuario)))
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = usuarioservice.delete(id);

        if (!deleted) {
            return ResponseEntity.notFound()
                    .build();
        }

        return ResponseEntity.noContent()
                .build();
    }

}
