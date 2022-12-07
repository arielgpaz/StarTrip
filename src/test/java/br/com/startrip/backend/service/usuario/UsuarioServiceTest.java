package br.com.startrip.backend.service.usuario;

import br.com.startrip.backend.controller.request.AtualizarUsuarioRequest;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.usuario.CpfJaCadastradoParaOutroUsuarioException;
import br.com.startrip.backend.exception.usuario.CpfUsuarioInexistenteException;
import br.com.startrip.backend.exception.usuario.EmailJaCadastradoParaOutroUsuarioException;
import br.com.startrip.backend.exception.usuario.IdUsuarioInexistenteException;
import br.com.startrip.backend.factories.UsuarioFactory;
import br.com.startrip.backend.repository.FotoUsuarioRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FotoUsuarioRepository fotoUsuarioRepository;

    private Usuario usuario;
    private AtualizarUsuarioRequest atualizarUsuarioRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = UsuarioFactory.criaUsuario();
        atualizarUsuarioRequest = UsuarioFactory.criaAtualizacaoUsuarioRequest();
    }

    @Test
    void givenDadosUsuarioValidos_whenCadastrar_thenReturnUsuarioCadastrado() {
        String linkFoto = "Link da foto";

        when(usuarioRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(usuarioRepository.existsByCpf(anyString()))
                .thenReturn(false);
        when(fotoUsuarioRepository.retornaFoto())
                .thenReturn(linkFoto);
        when(usuarioRepository.save(any()))
                .thenReturn(usuario);

        Usuario response = service.cadastrar(usuario);

        assertEquals(usuario.getCpf(), response.getCpf());
        assertEquals(usuario.getEmail(), response.getEmail());
        assertEquals(linkFoto, response.getFoto());
    }

    @Test
    void givenEmailUsadoPorOutroUsuario_whenCadastrar_thenThrowsEmailJaCadastradoParaOutroUsuarioException() {
        when(usuarioRepository.existsByEmail(anyString()))
                .thenReturn(true);

        assertThrows(EmailJaCadastradoParaOutroUsuarioException.class, () -> service.cadastrar(usuario));
    }

    @Test
    void givenCpfUsadoPorOutroUsuario_whenCadastrar_thenThrowsCpfJaCadastradoParaOutroUsuarioException() {
        when(usuarioRepository.existsByEmail(anyString()))
                .thenReturn(false);
        when(usuarioRepository.existsByCpf(anyString()))
                .thenReturn(true);

        assertThrows(CpfJaCadastradoParaOutroUsuarioException.class, () -> service.cadastrar(usuario));
    }

    @Test
    void whenListarUsuarios_thenReturnListaDeUsuarios() {
        when(usuarioRepository.findAll(any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(usuario)));

        Page<Usuario> response = service.listarUsuarios(PageRequest.of(0, 5));

        assertNotNull(response);
        assertEquals(1, response.getSize());
    }

    @Test
    void givenIdUsuarioValido_whenBuscarUsuarioPorId_thenReturnUsuario() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(usuario));

        Usuario response = service.buscarUsuarioPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void givenIdUsuarioInexistente_whenBuscarUsuarioPorId_thenThrowsIdUsuarioInexistenteException() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(IdUsuarioInexistenteException.class, () -> service.buscarUsuarioPorId(1L));
    }

    @Test
    void givenCpfUsuarioValido_whenBuscarUsuarioPorCpf_thenReturnUsuario() {
        when(usuarioRepository.findByCpf(anyString()))
                .thenReturn(Optional.ofNullable(usuario));

        Usuario response = service.buscarUsuarioPorCpf("12345678901");

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void givenCpfUsuarioInexistente_whenBuscarUsuarioPorCpf_thenThrowsCpfUsuarioInexistenteException() {
        when(usuarioRepository.findByCpf(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(CpfUsuarioInexistenteException.class, () -> service.buscarUsuarioPorCpf("12345678901"));
    }

    @Test
    void givenIdUsuarioValidoAndEmailDisponivel_whenAlterarUmUsuario_thenReturnUsuarioAlterado() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(usuario));
        when(usuarioRepository.existsByEmailAndIdIsNot(anyString(), anyLong()))
                .thenReturn(false);
        when(usuarioRepository.save(any()))
                .thenReturn(usuario);

        Usuario response = service.alterarUmUsuario(1L, atualizarUsuarioRequest);

        assertNotNull(response);
        assertEquals(atualizarUsuarioRequest.getNome(), response.getNome());
        assertEquals(atualizarUsuarioRequest.getEmail(), response.getEmail());
        assertEquals(atualizarUsuarioRequest.getSenha(), response.getSenha());

    }

    @Test
    void givenIdUsuarioInexistente_whenAlterarUmUsuario_thenThrowsIdUsuarioInexistenteException() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(IdUsuarioInexistenteException.class, () -> service.alterarUmUsuario(1L, atualizarUsuarioRequest));
    }

    @Test
    void givenIdUsuarioValidoAndEmailJaCadastrado_whenAlterarUmUsuario_thenThrowsEmailJaCadastradoParaOutroUsuarioException() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(usuario));
        when(usuarioRepository.existsByEmailAndIdIsNot(anyString(), anyLong()))
                .thenReturn(true);

        assertThrows(EmailJaCadastradoParaOutroUsuarioException.class, () -> service.alterarUmUsuario(1L, atualizarUsuarioRequest));
    }
}
