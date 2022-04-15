package br.com.startrip.backend.domain;

import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
