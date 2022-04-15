package br.com.startrip.backend.exception.reserva;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.startrip.backend.domain.FormaPagamento;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoException extends RuntimeException {
	public FormaPagamentoException(FormaPagamento formaPagamento, List<FormaPagamento> formasAceitas) {
		super(String.format("O anúncio não aceita %s como forma de pagamento. As formas aceitas são %s.", formaPagamento, formasAceitas)
				.replaceAll("[\\p{Ps}\\p{Pe}]", ""));
	}
}
