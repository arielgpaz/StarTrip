package br.com.startrip.backend.unity.service;

import br.com.startrip.backend.domain.Endereco;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.usuario.CpfUsuarioInexistenteException;
import br.com.startrip.backend.exception.usuario.IdUsuarioInexistenteException;
import br.com.startrip.backend.repository.UsuarioRepository;
import br.com.startrip.backend.service.usuario.UsuarioService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class   UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testeBuscarUsuarioPorId() {

        final Long idUsuario = 1L;

        Usuario usuario = Usuario.builder()
                .id(idUsuario)
                .nome("Usuário Um")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .cpf("12345678910")
                .email("teste01@teste.com")
                .endereco(Endereco.builder()
                        .id(1L)
                        .logradouro("Rua 01")
                        .numero("25")
                        .bairro("Centro")
                        .cep("12345-67")
                        .cidade("Pires do Rio")
                        .estado("Goias")
                        .complemento("")
                        .build())
                .build();
        usuarioRepository.save(usuario);

        Usuario usuarioEsperado = Usuario.builder()
                .id(1L)
                .nome("Usuário Um")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .cpf("12345678910")
                .email("teste01@teste.com")
                .endereco(Endereco.builder()
                        .id(1L)
                        .logradouro("Rua 01")
                        .numero("25")
                        .bairro("Centro")
                        .cep("12345-67")
                        .cidade("Pires do Rio")
                        .estado("Goias")
                        .complemento("")
                        .build())
                .build();

        Mockito.when(usuarioRepository.findById(idUsuario))
                .thenReturn(Optional.ofNullable(usuario));

        Usuario usuarioEncontrado = this.usuarioService.buscarUsuarioPorId(usuario.getId());

        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals(usuarioEsperado.getId(), usuarioEncontrado.getId());
    }

    @Test
    void testeBuscarUsuarioPorIdComErro() {
        final Long idUsuario = 1L;
        Assertions.assertThrows(IdUsuarioInexistenteException.class, () -> this.usuarioService.buscarUsuarioPorId(idUsuario));
    }

    @Test
    void testeBuscarUsuarioPorCpf() {

        final String cpfUsuario = "12345678910";

        Usuario usuario = Usuario.builder()
                .id(1L)
                .nome("Usuário Um")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .cpf(cpfUsuario)
                .email("teste01@teste.com")
                .senha("aaaa")
                .endereco(Endereco.builder()
                        .id(1L)
                        .logradouro("Rua 01")
                        .numero("25")
                        .bairro("Centro")
                        .cep("12345-67")
                        .cidade("Pires do Rio")
                        .estado("Goias")
                        .complemento("")
                        .build())
                .build();
        usuarioRepository.save(usuario);

        Mockito.when(usuarioRepository.findByCpf(cpfUsuario))
                .thenReturn(Optional.ofNullable(usuario));

        Usuario usuarioEncontrado = this.usuarioService.buscarUsuarioPorCpf(cpfUsuario);

        Assertions.assertNotNull(usuarioEncontrado);
        Assertions.assertEquals(usuario, usuarioEncontrado);
    }

    @Test
    void testeBuscarUsuarioPorCpfComErro() {
        final String cpfUsuario = "12345678910";
        Assertions.assertThrows(CpfUsuarioInexistenteException.class, () -> this.usuarioService.buscarUsuarioPorCpf(cpfUsuario));
    }
}