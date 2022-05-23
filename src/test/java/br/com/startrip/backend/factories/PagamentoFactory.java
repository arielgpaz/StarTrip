package br.com.startrip.backend.factories;

import java.math.BigDecimal;

import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.Pagamento;
import br.com.startrip.backend.domain.StatusPagamento;

public class PagamentoFactory {
	public static Pagamento criaPagamento(StatusPagamento statusPagamento) {
		return Pagamento.builder()
				.formaEscolhida(FormaPagamento.DINHEIRO)
				.valorTotal(BigDecimal.valueOf(500))
				.status(statusPagamento)
				.build();
	}
}
