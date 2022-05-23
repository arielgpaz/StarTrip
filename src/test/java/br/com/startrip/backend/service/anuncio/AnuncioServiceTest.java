package br.com.startrip.backend.service.anuncio;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.startrip.backend.controller.request.CadastrarAnuncioRequest;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.TipoAnuncio;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.factories.ImovelFactory;
import br.com.startrip.backend.factories.UsuarioFactory;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ImovelRepository;
import br.com.startrip.backend.repository.UsuarioRepository;

class AnuncioServiceTest {

	@InjectMocks
	private AnuncioService service;

	@Mock
	private ImovelRepository imovelRepository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private AnuncioRepository anuncioRepository;

	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void anunciarImovel_anunciarComSucesso_Test() {
		CadastrarAnuncioRequest anuncioRequest = CadastrarAnuncioRequest.builder()
				.tipoAnuncio(TipoAnuncio.COMPLETO)
				.valorDiaria(BigDecimal.TEN)
				.descricao("Descanse na casa de Jesus")
				.build();
		Imovel imovel = ImovelFactory.criaImovel();
		Usuario anunciante = UsuarioFactory.criaUsuarioAnunciante();
		Anuncio anuncio = Anuncio.builder()
				.tipoAnuncio(anuncioRequest.getTipoAnuncio())
				.imovel(imovel)
				.anunciante(anunciante)
				.valorDiaria(anuncioRequest.getValorDiaria())
				.formasAceitas(anuncioRequest.getFormasAceitas())
				.descricao(anuncioRequest.getDescricao())
				.build();

		when(imovelRepository.findByIdAndDeletedIs(any(), any())).thenReturn(Optional.ofNullable(imovel));
		when(anuncioRepository.existsByImovelIdAndDeletedIs(any(), any())).thenReturn(false);
		when(usuarioRepository.findById(any())).thenReturn(Optional.ofNullable(anunciante));
		when(anuncioRepository.save(any())).thenReturn(anuncio);

		Anuncio anuncioFeito = service.anunciarImovel(anuncioRequest);
	}

	@Test
	void listarAnuncios() {
	}

	@Test
	void listarAnunciosDeAnunciante() {
	}

	@Test
	void excluirAnuncio() {
	}
}