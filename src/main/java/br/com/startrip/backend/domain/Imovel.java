package br.com.startrip.backend.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Imovel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String identificacao;

	@Enumerated(EnumType.STRING)
	private TipoImovel tipoImovel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;

	@ManyToOne
	@JoinColumn(name = "id_proprietario")
	private Usuario proprietario;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "id_imovel")
	private List<CaracteristicaImovel> caracteristicas;

	@JsonProperty("deleted")
	private boolean deleted;

	public Imovel(String identificacao, TipoImovel tipoImovel, Endereco endereco, Usuario proprietario, List<CaracteristicaImovel> caracteristicas) {
		this.identificacao = identificacao;
		this.tipoImovel = tipoImovel;
		this.endereco = endereco;
		this.proprietario = proprietario;
		this.caracteristicas = caracteristicas;
	}

	@PrePersist
	void prePersist() {
		this.deleted = Boolean.FALSE;
	}
}
