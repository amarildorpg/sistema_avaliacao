package br.com.datanorte.sistemas_avaliacao.service;

import br.com.datanorte.sistemas_avaliacao.controller.request.UsuarioRequestDTO;
import br.com.datanorte.sistemas_avaliacao.entity.Usuario;
import br.com.datanorte.sistemas_avaliacao.entity.UsuarioToken;
import br.com.datanorte.sistemas_avaliacao.enums.Status;
import br.com.datanorte.sistemas_avaliacao.enums.TokenTipo;
import br.com.datanorte.sistemas_avaliacao.exception.UsernameOrPasswordInvalidException;
import br.com.datanorte.sistemas_avaliacao.mapper.UsuarioMapper;
import br.com.datanorte.sistemas_avaliacao.repository.UsuarioRepository;
import br.com.datanorte.sistemas_avaliacao.repository.UsuarioTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioTokenRepository usuarioTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Transactional
    public Usuario save(UsuarioRequestDTO dto) {
        String email = dto.email().trim().toLowerCase();

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new UsernameOrPasswordInvalidException("Email já cadastrado");
        }

        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(dto.password()));
        Usuario salvo = usuarioRepository.save(usuario);

        // Gera token de ativação e envia e-mail
        UsuarioToken token = UsuarioToken.gerar(salvo, TokenTipo.ATIVACAO);
        usuarioTokenRepository.save(token);
        emailService.enviarAtivacao(salvo.getEmail(), token.getToken());

        return salvo;
    }

    @Transactional
    public boolean ativarConta(String tokenStr) {
        Optional<UsuarioToken> optToken = usuarioTokenRepository
                .findByTokenAndTipo(tokenStr, TokenTipo.ATIVACAO);

        if (optToken.isEmpty() || optToken.get().isExpirado()) {
            return false;
        }

        Usuario usuario = optToken.get().getUsuario();
        usuario.setStatus(Status.ATIVO);
        usuarioRepository.save(usuario);
        usuarioTokenRepository.delete(optToken.get());

        return true;
    }

    @Transactional
    public boolean solicitarRecuperacaoSenha(String email) {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail(email.trim().toLowerCase());

        if (optUsuario.isEmpty()) {
            return false;
        }

        Usuario usuario = optUsuario.get();

        // Apaga token antigo se existir
        usuarioTokenRepository.deleteByUsuarioIdAndTipo(usuario.getId(), TokenTipo.RECUPERACAO_SENHA);
        usuarioTokenRepository.flush();
        UsuarioToken token = UsuarioToken.gerar(usuario, TokenTipo.RECUPERACAO_SENHA);
        usuarioTokenRepository.save(token);
        usuarioTokenRepository.flush();
        emailService.enviarRecuperacaoSenha(usuario.getEmail(), token.getToken());

        return true;
    }

    @Transactional
    public boolean redefinirSenha(String tokenStr, String novaSenha) {
        Optional<UsuarioToken> optToken = usuarioTokenRepository
                .findByTokenAndTipo(tokenStr, TokenTipo.RECUPERACAO_SENHA);

        if (optToken.isEmpty() || optToken.get().isExpirado()) {
            return false;
        }

        Usuario usuario = optToken.get().getUsuario();
        usuario.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        usuarioTokenRepository.delete(optToken.get());

        return true;
    }

    public boolean delete(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setStatus(Status.INATIVO);
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }

    public Optional<Usuario> update(Long userId, UsuarioRequestDTO dto) {
        return usuarioRepository.findById(userId).map(usuario -> {
            if (dto.name() != null && !dto.name().isEmpty()) {
                usuario.setName(dto.name());
            }
            if (dto.email() != null && !dto.email().isEmpty()) {
                usuario.setEmail(dto.email());
            }
            if (dto.password() != null && !dto.password().isBlank()) {
                usuario.setPassword(passwordEncoder.encode(dto.password()));
            }
            if (dto.status() != null) {
                usuario.setStatus(dto.status());
            }
            if (dto.perfil() != null) {
                usuario.setPerfil(dto.perfil());
            }
            return usuarioRepository.save(usuario);
        });
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}