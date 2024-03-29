package br.com.startrip.backend.factories;

import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.TipoImovel;

public class ImovelFactory {
	public static Imovel criaImovel() {
		return Imovel.builder()
				.id(12L)
				.endereco(EnderecoFactory.criaEndereco())
				.tipoImovel(TipoImovel.HOTEL)
				.identificacao("Hotel dos romeiros")
				.proprietario(UsuarioFactory.criaUsuarioAnunciante())
				.deleted(false)
				.build();
	}
}
