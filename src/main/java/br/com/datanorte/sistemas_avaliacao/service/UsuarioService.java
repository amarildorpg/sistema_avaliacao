package br.com.datanorte.sistemas_avaliacao.service;

import br.com.datanorte.sistemas_avaliacao.controller.request.UsuarioRequestDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import br.com.datanorte.sistemas_avaliacao.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    public Usuario save(Usuario usuario) {

        String email = usuario.getEmail().trim().toLowerCase();

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Já existe usuário com esse email");
        }

        usuario.setEmail(email);

        String senhaCriptografada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(senhaCriptografada);

        return usuarioRepository.save(usuario);
    }

    public boolean delete(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setStatus('0');
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }

    public Optional<Usuario> update(Long userId, UsuarioRequestDTO dto) {
        return  usuarioRepository.findById(userId).map(usuario -> {
            if (dto.name() != null && !dto.name().isEmpty()) {
                usuario.setName(dto.name());
            }
            if (dto.email() != null && !dto.email().isEmpty()) {
                usuario.setEmail(dto.email());
            }
            if (dto.password() != null && !dto.password().isBlank()) {
                usuario.setPassword(passwordEncoder.encode(dto.password()));
            }
            if (dto.status() != null){
                usuario.setStatus(dto.status());
            }
            if (dto.perfil() != null){
                usuario.setPerfil(dto.perfil());
            }
            return usuarioRepository.save(usuario);
        });

    }
    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }
}
