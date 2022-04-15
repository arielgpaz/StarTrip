package br.com.startrip.backend.controller.response;

import java.util.List;

import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;
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
public class DadosAnuncioResponse {

	private Long id;

	private Imovel imovel;

	private Usuario anunciante;

	private List<FormaPagamento> formasAceitas;

	private String descricao;

}
