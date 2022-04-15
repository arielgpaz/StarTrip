package br.com.startrip.backend.service.imovel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.startrip.backend.exception.anuncio.ImovelPossuiAnuncioException;
import br.com.startrip.backend.exception.imovel.IdImovelInexistenteException;
import br.com.startrip.backend.exception.imovel.ProprietarioNaoEncontradoPelaIdException;
import br.com.startrip.backend.repository.UsuarioRepository;
import br.com.startrip.backend.controller.request.CadastrarImovelRequest;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ImovelRepository;

@Service
public class ImovelService {

	@Autowired
	private ImovelRepository imovelRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AnuncioRepository anuncioRepository;

	public Imovel cadastrarImovel(CadastrarImovelRequest cadastrarImovelRequest) {

		Usuario proprietario = usuarioRepository.findById(cadastrarImovelRequest.getIdProprietario())
				.orElseThrow(() -> new ProprietarioNaoEncontradoPelaIdException(cadastrarImovelRequest.getIdProprietario()));

		Imovel imovel = new Imovel(cadastrarImovelRequest.getIdentificacao(), cadastrarImovelRequest.getTipoImovel(), cadastrarImovelRequest.getEndereco(), proprietario, cadastrarImovelRequest.getCaracteristicas());
		return imovelRepository.save(imovel);
	}

	public Page<Imovel> listarImoveis(Pageable pageable) {
		return imovelRepository.findByDeletedIs(false, pageable);
	}

	public Page<Imovel> listarImoveisDeProprietario(Pageable pageable, Long idProprietario) {
		return imovelRepository.findByProprietarioIdEqualsAndDeletedIsNot(pageable, idProprietario, true);
	}

	public Imovel listarImoveisPorId(Long idImovel) {
		return imovelRepository.findByIdAndDeletedIsNot(idImovel, true)
				.orElseThrow(() -> new IdImovelInexistenteException(idImovel));
	}

	public void excluirImovel(Long idImovel) {
		Imovel imovelParaExcluir = imovelRepository.findByIdAndDeletedIsNot(idImovel, true)
				.orElseThrow(() -> new IdImovelInexistenteException(idImovel));

		if (anuncioRepository.existsByImovelAndDeletedIs(imovelParaExcluir, false)) {
			throw new ImovelPossuiAnuncioException();
		}

		imovelParaExcluir.setDeleted(true);
		imovelRepository.save(imovelParaExcluir);
	}
}
