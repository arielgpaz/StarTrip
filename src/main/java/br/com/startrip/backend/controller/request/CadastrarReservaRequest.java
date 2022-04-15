package br.com.startrip.backend.controller.request;

import javax.validation.constraints.NotNull;

import br.com.startrip.backend.domain.Periodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarReservaRequest {

	public static final String MINHA = "kjasdf";

	@NotNull
	private Long idSolicitante;

	@NotNull
	private Long idAnuncio;

	@NotNull
	private Periodo periodo;

	@NotNull
	private Integer quantidadePessoas;

}
