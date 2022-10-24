package br.com.startrip.backend.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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
	@ToString.Exclude
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Imovel imovel = (Imovel) o;
		return id != null && Objects.equals(id, imovel.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
