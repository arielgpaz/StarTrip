package br.com.startrip.backend.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve ser informado no formato 99999-999.")
	private String cep;

	@NotNull
	private String logradouro;

	@NotNull
	private String numero;

	private String complemento;

	@NotNull
	private String bairro;

	@NotNull
	private String cidade;

	@NotNull
	private String estado;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Endereco endereco = (Endereco) o;
		return id != null && Objects.equals(id, endereco.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
