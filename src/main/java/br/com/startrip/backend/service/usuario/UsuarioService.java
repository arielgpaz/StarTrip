package br.com.startrip.backend.service.usuario;

import br.com.startrip.backend.controller.request.AtualizarUsuarioRequest;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.usuario.CpfJaCadastradoParaOutroUsuarioException;
import br.com.startrip.backend.exception.usuario.CpfUsuarioInexistenteException;
import br.com.startrip.backend.exception.usuario.EmailJaCadastradoParaOutroUsuarioException;
import br.com.startrip.backend.exception.usuario.IdUsuarioInexistenteException;
import br.com.startrip.backend.repository.FotoUsuarioRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FotoUsuarioRepository fotoUsuarioRepository;

    public Usuario cadastrar(Usuario usuario) {

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EmailJaCadastradoParaOutroUsuarioException(usuario.getEmail());
        }

        if (usuarioRepository.existsByCpf(usuario.getCpf())) {
            throw new CpfJaCadastradoParaOutroUsuarioException(usuario.getCpf());
        }

        usuario.setFoto(fotoUsuarioRepository.retornaFoto());

        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IdUsuarioInexistenteException(id));
    }

    public Usuario buscarUsuarioPorCpf(String cpf) {
        return usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new CpfUsuarioInexistenteException(cpf));
    }

    public Usuario alterarUmUsuario(Long id, AtualizarUsuarioRequest atualizarUsuarioRequest) {

        Usuario usuarioEncontrado = buscarUsuarioPorId(id);

        if (usuarioRepository.existsByEmailAndIdIsNot(atualizarUsuarioRequest.getEmail(), id)) {
            throw new EmailJaCadastradoParaOutroUsuarioException(atualizarUsuarioRequest.getEmail());
        }

        usuarioEncontrado.setNome(atualizarUsuarioRequest.getNome());
        usuarioEncontrado.setEmail(atualizarUsuarioRequest.getEmail());
        usuarioEncontrado.setSenha(atualizarUsuarioRequest.getSenha());
        usuarioEncontrado.setDataNascimento(atualizarUsuarioRequest.getDataNascimento());

        if (atualizarUsuarioRequest.getEndereco() != null) {
            usuarioEncontrado.setEndereco(atualizarUsuarioRequest.getEndereco());
        }

        return usuarioRepository.save(usuarioEncontrado);
    }
}
