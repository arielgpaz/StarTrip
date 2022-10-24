package br.com.startrip.backend.service.anuncio;

import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ImovelRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	void listarAnuncios() {
	}

	@Test
	void listarAnunciosDeAnunciante() {
	}

	@Test
	void excluirAnuncio() {
	}
}