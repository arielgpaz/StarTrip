package br.com.startrip.backend.exception.reserva;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatusPagamentoException extends RuntimeException {
	public StatusPagamentoException(String message) {
		super(message);
	}
}
