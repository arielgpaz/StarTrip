package br.com.startrip.backend.factories;

import br.com.startrip.backend.controller.request.CadastrarAnuncioRequest;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.TipoAnuncio;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class AnuncioFactory {
	public static Anuncio criaAnuncio() {
		return Anuncio.builder()
				.id(1L)
				.descricao("Quarto com sacada de frente para a praia")
				.tipoAnuncio(TipoAnuncio.QUARTO)
				.anunciante(UsuarioFactory.criaUsuario())
				.imovel(ImovelFactory.criaImovel())
				.valorDiaria(BigDecimal.valueOf(100))
				.formasAceitas(Arrays.asList(FormaPagamento.DINHEIRO, FormaPagamento.CARTAO_CREDITO, FormaPagamento.CARTAO_DEBITO))
				.deleted(false)
				.build();
	}

	public static CadastrarAnuncioRequest criarAnuncioRequest() {
		return CadastrarAnuncioRequest.builder()
				.idImovel(1L)
				.idAnunciante(1L)
				.tipoAnuncio(TipoAnuncio.COMPLETO)
				.valorDiaria(BigDecimal.TEN)
				.formasAceitas(List.of(FormaPagamento.DINHEIRO, FormaPagamento.PIX))
				.descricao("A diversão está aqui")
				.build();
	}
}
