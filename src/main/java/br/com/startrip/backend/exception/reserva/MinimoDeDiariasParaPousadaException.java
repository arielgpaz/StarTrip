package br.com.startrip.backend.exception.reserva;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MinimoDeDiariasParaPousadaException extends RuntimeException {
	public MinimoDeDiariasParaPousadaException() {
		super("Não é possivel realizar uma reserva com menos de 5 diárias para imóveis do tipo Pousada");
	}
}
