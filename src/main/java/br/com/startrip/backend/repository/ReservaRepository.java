package br.com.startrip.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.startrip.backend.domain.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	List<Reserva> findByAnuncioId(Long id);

	Page<Reserva> findByAnuncioAnuncianteId(Pageable pageable, Long idAnunciante);

	Page<Reserva> findBySolicitanteIdAndPeriodoDataHoraInicialBetweenAndPeriodoDataHoraFinalBetween(Pageable pageable, Long idSolicitante, LocalDateTime dataHoraInicial1, LocalDateTime dataHoraFinal1, LocalDateTime dataHoraInicial2, LocalDateTime dataHoraFinal2);

	Page<Reserva> findBySolicitanteId(Pageable pageable, Long idSolicitante);
}
