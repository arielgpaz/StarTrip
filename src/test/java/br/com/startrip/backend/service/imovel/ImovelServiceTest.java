package br.com.startrip.backend.service.imovel;

import br.com.startrip.backend.controller.request.CadastrarImovelRequest;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.anuncio.ImovelPossuiAnuncioException;
import br.com.startrip.backend.exception.imovel.IdImovelInexistenteException;
import br.com.startrip.backend.exception.imovel.ProprietarioNaoEncontradoPelaIdException;
import br.com.startrip.backend.factories.ImovelFactory;
import br.com.startrip.backend.factories.UsuarioFactory;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ImovelRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImovelServiceTest {

    @InjectMocks
    private ImovelService service;

    @Mock
    private ImovelRepository imovelRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AnuncioRepository anuncioRepository;

    @Captor
    ArgumentCaptor<Imovel> imovelCaptor;

    private CadastrarImovelRequest request;
    private Usuario proprietario;
    private Imovel imovel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = ImovelFactory.criaImovelRequest();
        proprietario = UsuarioFactory.criaUsuarioAnunciante();
        imovel = ImovelFactory.criaImovel();
    }

    @Test
    void givenImovelRequestValido_whenCadastrarImovel_thenCadastrarImovelComSucesso() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(proprietario));
        when(imovelRepository.save(any()))
                .thenReturn(imovel);

        service.cadastrarImovel(request);

        verify(imovelRepository).save(imovelCaptor.capture());
        Imovel imovelSalvo = imovelCaptor.getValue();
        assertEquals(request.getIdentificacao(), imovelSalvo.getIdentificacao());
    }

    @Test
    void givenIdProprietarioInexistente_whenCadastrarImovel_thenThrowsProprietarioNaoEncontradoPelaIdException() {
        when(usuarioRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(ProprietarioNaoEncontradoPelaIdException.class, () -> service.cadastrarImovel(request));
    }

    @Test
    void whenListarImoveis_thenReturnPageOfImoveis() {
        when(imovelRepository.findByDeletedIs(anyBoolean(), any()))
                .thenReturn(new PageImpl<>(List.of(imovel)));

        Page<Imovel> response = service.listarImoveis(PageRequest.of(0, 5));

        assertNotNull(response);
        assertEquals(1, response.getSize());
    }

    @Test
    void whenListarImoveisDeProprietario_thenReturnPageOfImoveis() {
        when(imovelRepository.findByProprietarioIdEqualsAndDeletedIs(any(), any(), anyBoolean()))
                .thenReturn(new PageImpl<>(List.of(imovel)));

        Page<Imovel> response = service.listarImoveisDeProprietario(PageRequest.of(0, 5), 1L);

        assertNotNull(response);
        assertEquals(1, response.getSize());
    }

    @Test
    void givenIdImovelValido_whenListarImoveisPorId_thenReturnImovel() {
        when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
                .thenReturn(Optional.ofNullable(imovel));

        Imovel response = service.listarImoveisPorId(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void givenIdImovelInexistente_whenListarImoveisPorId_thenThrowsIdImovelInexistenteException() {
        when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
                .thenReturn(Optional.empty());

        assertThrows(IdImovelInexistenteException.class, () -> service.listarImoveisPorId(1L));
    }

    @Test
    void givenIdImovelValido_whenExcluirImovel_thenSetImovelDeletedAndSave() {
        when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
                .thenReturn(Optional.ofNullable(imovel));
        when(anuncioRepository.existsByImovelAndDeletedIs(any(), anyBoolean()))
                .thenReturn(false);
        when(imovelRepository.save(any()))
                .thenReturn(imovel);

        service.excluirImovel(1L);

        verify(imovelRepository).save(imovelCaptor.capture());
        Imovel imovelExcluido = imovelCaptor.getValue();
        assertTrue(imovelExcluido.isDeleted());
    }

    @Test
    void givenIdImovelInexistente_whenExcluirImovel_thenThrowsIdImovelInexistenteException() {
        when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
                .thenReturn(Optional.empty());

        assertThrows(IdImovelInexistenteException.class, () -> service.excluirImovel(1L));
    }

    @Test
    void givenIdImovelComAnuncioAtivo_whenExcluirImovel_thenThrowsImovelPossuiAnuncioException() {
        when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
                .thenReturn(Optional.ofNullable(imovel));
        when(anuncioRepository.existsByImovelAndDeletedIs(any(), anyBoolean()))
                .thenReturn(true);

        assertThrows(ImovelPossuiAnuncioException.class, () -> service.excluirImovel(1L));
    }
}
