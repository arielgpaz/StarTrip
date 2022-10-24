package br.com.startrip.backend.controller.response;

import br.com.startrip.backend.domain.Pagamento;
import br.com.startrip.backend.domain.Periodo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InformacaoReservaResponse {

	private Long idReserva;

	private DadosSolicitanteResponse solicitante;

	private Integer quantidadePessoas;

	private DadosAnuncioResponse anuncio;

	private Periodo periodo;

	private Pagamento pagamento;
}
