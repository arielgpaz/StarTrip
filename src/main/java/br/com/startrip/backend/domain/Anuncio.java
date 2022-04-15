package br.com.startrip.backend.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
}
