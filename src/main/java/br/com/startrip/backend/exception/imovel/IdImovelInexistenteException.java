package br.com.startrip.backend.exception.imovel;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdImovelInexistenteException extends RuntimeException {
	public IdImovelInexistenteException(Long id) {
		super(String.format("Nenhum(a) Imovel com Id com o valor '%d' foi encontrado.", id));
	}
}
