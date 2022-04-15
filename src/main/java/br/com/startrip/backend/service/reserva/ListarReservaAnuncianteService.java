package br.com.startrip.backend.service.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.repository.ReservaRepository;

@Service
public class ListarReservaAnuncianteService {

	@Autowired
	private ReservaRepository reservaRepository;

	public Page<Reserva> listarReservaDeAnunciante(Pageable pageable, Long idAnunciante) {
		return reservaRepository.findByAnuncioAnuncianteId(pageable, idAnunciante);
	}
}
