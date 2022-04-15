package br.com.startrip.backend.exception.usuario;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfJaCadastradoParaOutroUsuarioException extends RuntimeException {

	public CpfJaCadastradoParaOutroUsuarioException(@NotNull String cpf) {
		super(String.format("Já existe um recurso do tipo Usuario com CPF com o valor '%s'.", cpf));
	}
}
