package br.com.startrip.backend.factories;

import br.com.startrip.backend.domain.Endereco;

public class EnderecoFactory {
	public static Endereco criaEndereco() {
		return Endereco.builder()
				.id(26L)
				.cep("11222-333")
				.logradouro("Rua dos Anjos")
				.numero("2")
				.bairro("Centro")
				.cidade("Louvores")
				.complemento("Hotel com jardim na entrada")
				.estado("Primeiro Estado")
				.build();
	}
}
