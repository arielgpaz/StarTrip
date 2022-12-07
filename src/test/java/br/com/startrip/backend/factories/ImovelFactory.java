package br.com.startrip.backend.factories;

import br.com.startrip.backend.controller.request.CadastrarImovelRequest;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.TipoImovel;

import java.util.List;

public class ImovelFactory {
	public static Imovel criaImovel() {
		return Imovel.builder()
				.id(1L)
				.endereco(EnderecoFactory.criaEndereco())
				.tipoImovel(TipoImovel.HOTEL)
				.identificacao("Hotel dos romeiros")
				.proprietario(UsuarioFactory.criaUsuarioAnunciante())
				.deleted(false)
				.build();
	}

	public static CadastrarImovelRequest criaImovelRequest() {
		return CadastrarImovelRequest.builder()
				.identificacao("Casa do lago")
				.tipoImovel(TipoImovel.CASA)
				.endereco(EnderecoFactory.criaEndereco())
				.idProprietario(1L)
				.caracteristicas(List.of(CaracteristicaImovelFactory.criaCaracteristicaImovel()))
				.build();
	}
}
