package br.com.startrip.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagamento {

	private BigDecimal valorTotal;

	@Enumerated(EnumType.STRING)
	private FormaPagamento formaEscolhida;

	@Enumerated(EnumType.STRING)
	private StatusPagamento status;

}
