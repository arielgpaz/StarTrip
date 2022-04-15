package br.com.startrip.backend.service.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.startrip.backend.domain.Periodo;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.repository.ReservaRepository;

@Service
public class ListarReservaSolicitanteService {

	@Autowired
	private ReservaRepository reservaRepository;

	public Page<Reserva> listarReservaDeSolicitante(Pageable pageable, Long idSolicitante, Periodo periodo) {

		Page<Reserva> reservasDoSolicitante;

		if (periodo != null && periodo.getDataHoraInicial() != null && periodo.getDataHoraFinal() != null) {
			reservasDoSolicitante = reservaRepository.findBySolicitanteIdAndPeriodoDataHoraInicialBetweenAndPeriodoDataHoraFinalBetween(pageable, idSolicitante, periodo.getDataHoraInicial(), periodo.getDataHoraFinal(), periodo.getDataHoraInicial(), periodo.getDataHoraFinal());
		} else {
			reservasDoSolicitante = reservaRepository.findBySolicitanteId(pageable, idSolicitante);
		}
		return reservasDoSolicitante;
	}
}
