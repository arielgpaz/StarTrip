package br.com.startrip.backend.controller.request;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.startrip.backend.domain.CaracteristicaImovel;
import br.com.startrip.backend.domain.Endereco;
import br.com.startrip.backend.domain.TipoImovel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CadastrarImovelRequest {

	@NotEmpty
	private String identificacao;

	@Enumerated(EnumType.STRING)
	@NotNull
	private TipoImovel tipoImovel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	@NotNull
	@Valid
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "id_proprietario")
	@NotNull
	private Long idProprietario;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "id_imovel")
	private List<CaracteristicaImovel> caracteristicas;

}
