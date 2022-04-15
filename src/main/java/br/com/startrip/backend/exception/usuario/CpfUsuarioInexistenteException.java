package br.com.startrip.backend.exception.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CpfUsuarioInexistenteException extends RuntimeException {
	public CpfUsuarioInexistenteException(String cpf) {
		super(String.format("Nenhum(a) Usuario com CPF com o valor '%s' foi encontrado.", cpf));
	}
}
