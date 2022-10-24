package br.com.startrip.backend.controller.response;

import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
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
