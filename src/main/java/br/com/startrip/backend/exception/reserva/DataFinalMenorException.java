package br.com.startrip.backend.exception.reserva;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataFinalMenorException extends RuntimeException {
	public DataFinalMenorException() {
		super("Período inválido! A data final da reserva precisa ser maior do que a data inicial.");
	}
}
