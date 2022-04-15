package br.com.startrip.backend.exception.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdUsuarioInexistenteException extends RuntimeException {
	public IdUsuarioInexistenteException(Long id) {
		super(String.format("Nenhum(a) Usuario com Id com o valor '%d' foi encontrado.", id));
	}
}
