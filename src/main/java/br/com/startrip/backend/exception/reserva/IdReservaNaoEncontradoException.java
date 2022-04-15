package br.com.startrip.backend.exception.reserva;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdReservaNaoEncontradoException extends RuntimeException {
	public IdReservaNaoEncontradoException(Long idReserva) {
		super(String.format("Nenhum(a) Reserva com Id com o valor '%d' foi encontrado.", idReserva));
	}
}
