package br.com.startrip.backend.service.anuncio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.startrip.backend.exception.anuncio.IdAnuncioNaoEncontradoException;
import br.com.startrip.backend.exception.anuncio.ImovelJaAnunciadoException;
import br.com.startrip.backend.exception.imovel.IdImovelInexistenteException;
import br.com.startrip.backend.controller.request.CadastrarAnuncioRequest;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.usuario.IdUsuarioInexistenteException;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ImovelRepository;
import br.com.startrip.backend.repository.UsuarioRepository;

@Service
public class AnuncioService {

	@Autowired
	private ImovelRepository imovelRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AnuncioRepository anuncioRepository;

	public Anuncio anunciarImovel(CadastrarAnuncioRequest cadastrarAnuncioRequest) {
		Imovel imovelParaCadastrar = imovelRepository.findByIdAndDeletedIs(cadastrarAnuncioRequest.getIdImovel(), false)
				.orElseThrow(() -> new IdImovelInexistenteException(cadastrarAnuncioRequest.getIdImovel()));

		if (anuncioRepository.existsByImovelIdAndDeletedIs(cadastrarAnuncioRequest.getIdImovel(), false)) {
			throw new ImovelJaAnunciadoException(cadastrarAnuncioRequest.getIdImovel());
		}

		Usuario anunciante = usuarioRepository.findById(cadastrarAnuncioRequest.getIdAnunciante())
				.orElseThrow(() -> new IdUsuarioInexistenteException(cadastrarAnuncioRequest.getIdAnunciante()));

		Anuncio anuncio = Anuncio.builder()
				.tipoAnuncio(cadastrarAnuncioRequest.getTipoAnuncio())
				.imovel(imovelParaCadastrar)
				.anunciante(anunciante)
				.valorDiaria(cadastrarAnuncioRequest.getValorDiaria())
				.formasAceitas(cadastrarAnuncioRequest.getFormasAceitas())
				.descricao(cadastrarAnuncioRequest.getDescricao())
				.build();

		return anuncioRepository.save(anuncio);
	}

	public Page<Anuncio> listarAnuncios(Pageable pageable) {
		return anuncioRepository.findByDeletedIs(false, pageable);
	}

	public Page<Anuncio> listarAnunciosDeAnunciante(Pageable pageable, Long idAnunciante) {
		Usuario usuario = usuarioRepository.findById(idAnunciante)
				.orElseThrow(() -> new IdUsuarioInexistenteException(idAnunciante));
		return anuncioRepository.findByAnuncianteAndDeletedIs(pageable, usuario, false);
	}

	public void excluirAnuncio(Long idAnuncio) {
		Anuncio anuncioParaExcluir = anuncioRepository.findByIdAndDeletedIs(idAnuncio, false)
				.orElseThrow(() -> new IdAnuncioNaoEncontradoException(idAnuncio));
		anuncioParaExcluir.setDeleted(true);
		anuncioRepository.save(anuncioParaExcluir);
	}
}
