package br.com.startrip.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "deleted" }, allowSetters = true)
public class Anuncio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TipoAnuncio tipoAnuncio;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_imovel")
	private Imovel imovel;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_anunciante")
	private Usuario anunciante;

	private BigDecimal valorDiaria;

	@ElementCollection
	@Enumerated(EnumType.STRING)
	private List<FormaPagamento> formasAceitas;

	private String descricao;

	@JsonProperty("deleted")
	private boolean deleted;

	public Anuncio(TipoAnuncio tipoAnuncio, Imovel imovel, Usuario anunciante, BigDecimal valorDiaria, List<FormaPagamento> formasAceitas, String descricao) {
		this.tipoAnuncio = tipoAnuncio;
		this.imovel = imovel;
		this.anunciante = anunciante;
		this.valorDiaria = valorDiaria;
		this.formasAceitas = formasAceitas;
		this.descricao = descricao;
	}

	@PrePersist
	void prePersist() {
		this.deleted = Boolean.FALSE;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Anuncio anuncio = (Anuncio) o;
		return id != null && Objects.equals(id, anuncio.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
