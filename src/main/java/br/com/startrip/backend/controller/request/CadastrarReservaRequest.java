package br.com.startrip.backend.controller.request;

import br.com.startrip.backend.domain.Periodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
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
