package br.com.startrip.backend.exception.imovel;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProprietarioNaoEncontradoPelaIdException extends RuntimeException {
	public ProprietarioNaoEncontradoPelaIdException(@NotNull Long idProprietario) {
		super(String.format("Nenhum(a) Usuario com Id com o valor '%d' foi encontrado.", idProprietario));
	}
}
