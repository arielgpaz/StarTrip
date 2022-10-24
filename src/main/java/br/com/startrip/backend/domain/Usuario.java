package br.com.startrip.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(value = { "senha" }, allowSetters = true)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nome;

	@NotEmpty
	@Email
	private String email;

	@NotEmpty
	@JsonProperty("senha")
	private String senha;

	@NotEmpty
	@Pattern(regexp = "\\d{11}", message = "O CPF deve ser informado no formato 99999999999.")
	private String cpf;

	@NotNull
	private LocalDate dataNascimento;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	@Valid
	private Endereco endereco;

	private String foto;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Usuario usuario = (Usuario) o;
		return id != null && Objects.equals(id, usuario.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
