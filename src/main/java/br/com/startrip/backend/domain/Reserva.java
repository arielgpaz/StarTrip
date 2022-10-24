package br.com.startrip.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_solicitante")
	private Usuario solicitante;

	@ManyToOne
	@JoinColumn(name = "id_anuncio")
	private Anuncio anuncio;

	@Embedded
	private Periodo periodo;

	private Integer quantidadePessoas;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataHoraReserva;

	@Embedded
	private Pagamento pagamento;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Reserva reserva = (Reserva) o;
		return id != null && Objects.equals(id, reserva.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
