package br.com.startrip.backend.controller.request;

import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.TipoAnuncio;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CadastrarAnuncioRequest {

	@NotNull
	private Long idImovel;

	@NotNull
	private Long idAnunciante;

	@NotNull
	private TipoAnuncio tipoAnuncio;

	@NotNull
	private BigDecimal valorDiaria;

	@ElementCollection
	@NotNull
	private List<FormaPagamento> formasAceitas;

	@NotEmpty
	private String descricao;
}
