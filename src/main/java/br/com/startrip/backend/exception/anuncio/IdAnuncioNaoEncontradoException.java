package br.com.startrip.backend.exception.anuncio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class IdAnuncioNaoEncontradoException extends RuntimeException {
	public IdAnuncioNaoEncontradoException(Long idAnuncio) {
		super(String.format("Nenhum(a) Anuncio com Id com o valor '%d' foi encontrado.", idAnuncio));
	}
}
