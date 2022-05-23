package br.com.startrip.backend.factories;

import java.math.BigDecimal;
import java.util.Arrays;

import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.TipoAnuncio;

public class AnuncioFactory {
	public static Anuncio criaAnuncio() {
		return Anuncio.builder()
				.id(36L)
				.descricao("Quarto com sacada de frente para a praia")
				.tipoAnuncio(TipoAnuncio.QUARTO)
				.anunciante(UsuarioFactory.criaUsuarioAnunciante())
				.imovel(ImovelFactory.criaImovel())
				.valorDiaria(BigDecimal.valueOf(100))
				.formasAceitas(Arrays.asList(FormaPagamento.DINHEIRO, FormaPagamento.CARTAO_CREDITO, FormaPagamento.CARTAO_DEBITO))
				.build();
	}
}
