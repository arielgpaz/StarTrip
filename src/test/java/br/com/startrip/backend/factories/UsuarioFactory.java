package br.com.startrip.backend.factories;

import br.com.startrip.backend.controller.request.AtualizarUsuarioRequest;
import br.com.startrip.backend.domain.Usuario;

import java.time.LocalDate;

public class UsuarioFactory {
	public static Usuario criaUsuario() {
		return Usuario.builder()
				.id(1L)
				.nome("Jesus")
				.cpf("12345678901")
				.dataNascimento(LocalDate.of(0, 12, 25))
				.email("jesus@ceu.com.br")
				.senha("123456")
				.endereco(EnderecoFactory.criaEndereco())
				.build();
	}

	public static Usuario criaUsuarioSolicitante() {
		return Usuario.builder()
				.id(2L)
				.nome("João")
				.cpf("12345678902")
				.dataNascimento(LocalDate.of(2, 1, 18))
				.email("joao@ceu.com.br")
				.senha("abcdef")
				.endereco(EnderecoFactory.criaEndereco())
				.build();
	}

	public static AtualizarUsuarioRequest criaAtualizacaoUsuarioRequest() {
		return AtualizarUsuarioRequest.builder()
				.nome("João Batista")
				.email("jb@ceu.com.br")
				.senha("novaSenha")
				.dataNascimento(LocalDate.of(1, 3, 25))
				.endereco(EnderecoFactory.criaEndereco())
				.build();
	}
}
