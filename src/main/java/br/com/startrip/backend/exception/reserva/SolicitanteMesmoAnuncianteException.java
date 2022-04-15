package br.com.startrip.backend.exception.reserva;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SolicitanteMesmoAnuncianteException extends RuntimeException {
	public SolicitanteMesmoAnuncianteException() {
		super("O solicitante de uma reserva não pode ser o próprio anunciante.");
	}
}
