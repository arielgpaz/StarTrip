package br.com.startrip.backend.service.anuncio;

import br.com.startrip.backend.controller.request.CadastrarAnuncioRequest;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.anuncio.ImovelJaAnunciadoException;
import br.com.startrip.backend.exception.imovel.IdImovelInexistenteException;
import br.com.startrip.backend.exception.usuario.IdUsuarioInexistenteException;
import br.com.startrip.backend.factories.AnuncioFactory;
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

class AnuncioServiceTest {

	@InjectMocks
	private AnuncioService service;

	@Mock
	private ImovelRepository imovelRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private AnuncioRepository anuncioRepository;

	@Captor
	ArgumentCaptor<Anuncio> anuncioCaptor;

	Imovel imovel;
	Usuario usuario;
	Anuncio anuncio;
	CadastrarAnuncioRequest request;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
		request = AnuncioFactory.criarAnuncioRequest();
		imovel = ImovelFactory.criaImovel();
		usuario = UsuarioFactory.criaUsuario();
		anuncio = AnuncioFactory.criaAnuncio();
	}

	@Test
	void givenAnuncioRequestValido_whenAnunciarImovel_thenCadastrarAnuncioComSucesso() {

		when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(Optional.ofNullable(imovel));
		when(anuncioRepository.existsByImovelIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(false);
		when(usuarioRepository.findById(anyLong()))
				.thenReturn(Optional.ofNullable(usuario));
		when(anuncioRepository.save(any()))
				.thenReturn(anuncio);

		service.anunciarImovel(request);

		verify(anuncioRepository).save(anuncioCaptor.capture());
		Anuncio anuncioSalvo = anuncioCaptor.getValue();
		assertEquals(request.getIdImovel(), anuncioSalvo.getImovel().getId());
		assertEquals(request.getIdAnunciante(), anuncioSalvo.getAnunciante().getId());
	}

	@Test
	void givenIdImovelInexistente_whenAnunciarImovel_thenThrowIdImovelInexistenteException() {

		when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(Optional.empty());

		assertThrows(IdImovelInexistenteException.class, () -> service.anunciarImovel(request));
	}

	@Test
	void givenIdImovelJaAnunciado_whenAnunciarImovel_thenThrowsImovelJaAnunciadoException() {

		when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(Optional.ofNullable(imovel));
		when(anuncioRepository.existsByImovelIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(true);

		assertThrows(ImovelJaAnunciadoException.class, () -> service.anunciarImovel(request));
	}

	@Test
	void givenIdUsuarioInexistente_whenAnunciarImovel_thenThrowsIdUsuarioInexistenteException() {

		when(imovelRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(Optional.ofNullable(imovel));
		when(anuncioRepository.existsByImovelIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(false);
		when(usuarioRepository.findById(anyLong()))
				.thenReturn(Optional.empty());

		assertThrows(IdUsuarioInexistenteException.class, () -> service.anunciarImovel(request));
	}

	@Test
	void whenListarAnuncios_thenReturnPageOfAnuncios() {
		when(anuncioRepository.findByDeletedIs(anyBoolean(), any()))
				.thenReturn(new PageImpl<>(List.of(anuncio)));

		Page<Anuncio> response = service.listarAnuncios(PageRequest.of(0, 5));

		assertNotNull(response);
		assertEquals(1, response.getSize());
	}

	@Test
	void givenIdAnuncianteValido_whenListarAnunciosDeAnunciante_thenRetornarAnunciosComSucesso() {
		when(usuarioRepository.findById(anyLong()))
				.thenReturn(Optional.ofNullable(usuario));
		when(anuncioRepository.findByAnuncianteAndDeletedIs(any(), any(), anyBoolean()))
				.thenReturn(new PageImpl<>(List.of(anuncio)));

		Page<Anuncio> response = service.listarAnunciosDeAnunciante(PageRequest.of(0, 5), 1L);

		assertEquals(1, response.getSize());
	}

	@Test
	void givenIdAnuncianteInexistente_whenListarAnunciosDeAnunciante_thenThrowsIdUsuarioInexistenteException() {
		PageRequest pageRequest = PageRequest.of(0, 5);

		when(usuarioRepository.findById(anyLong()))
				.thenReturn(Optional.empty());

		assertThrows(IdUsuarioInexistenteException.class,
				() -> service.listarAnunciosDeAnunciante(pageRequest, 1L));
	}

	@Test
	void givenIdAnuncioValido_whenExcluirAnuncio_then() {
		when(anuncioRepository.findByIdAndDeletedIs(anyLong(), anyBoolean()))
				.thenReturn(Optional.ofNullable(anuncio));
		when(anuncioRepository.save(any()))
				.thenReturn(new Anuncio());

		service.excluirAnuncio(1L);

		assertTrue(anuncio.isDeleted());
	}

	@Test
	void givenIdAnuncioValido_whenBuscarAnuncioPorId_thenReturnAnuncio() {
		when(anuncioRepository.findById(anyLong()))
				.thenReturn(Optional.ofNullable(anuncio));

		Anuncio response = service.buscarAnuncioPorId(1L);

		assertNotNull(response);
		assertEquals(1L, response.getId());
	}
}
