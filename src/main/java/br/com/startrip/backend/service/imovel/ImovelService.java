package br.com.startrip.backend.service.imovel;

import br.com.startrip.backend.controller.request.CadastrarImovelRequest;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.anuncio.ImovelPossuiAnuncioException;
import br.com.startrip.backend.exception.imovel.IdImovelInexistenteException;
import br.com.startrip.backend.exception.imovel.ProprietarioNaoEncontradoPelaIdException;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ImovelRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        return imovelRepository.save(Imovel.builder()
                .identificacao(cadastrarImovelRequest.getIdentificacao())
                .tipoImovel(cadastrarImovelRequest.getTipoImovel())
                .endereco(cadastrarImovelRequest.getEndereco())
                .proprietario(proprietario)
                .caracteristicas(cadastrarImovelRequest.getCaracteristicas())
                .build());
    }

    public Page<Imovel> listarImoveis(Pageable pageable) {
        return imovelRepository.findByDeletedIs(false, pageable);
    }

    public Page<Imovel> listarImoveisDeProprietario(Pageable pageable, Long idProprietario) {
        return imovelRepository.findByProprietarioIdEqualsAndDeletedIs(pageable, idProprietario, false);
    }

    public Imovel listarImoveisPorId(Long idImovel) {
        return imovelRepository.findByIdAndDeletedIs(idImovel, false)
                .orElseThrow(() -> new IdImovelInexistenteException(idImovel));
    }

    public void excluirImovel(Long idImovel) {
        Imovel imovelParaExcluir = imovelRepository.findByIdAndDeletedIs(idImovel, false)
                .orElseThrow(() -> new IdImovelInexistenteException(idImovel));

        if (anuncioRepository.existsByImovelAndDeletedIs(imovelParaExcluir, false)) {
            throw new ImovelPossuiAnuncioException();
        }

        imovelParaExcluir.setDeleted(true);
        imovelRepository.save(imovelParaExcluir);
    }
}
