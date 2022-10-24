package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.exception.reserva.IdReservaNaoEncontradoException;
import br.com.startrip.backend.exception.reserva.StatusPagamentoException;
import br.com.startrip.backend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstornarReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	public void estornarReserva(final Long idReserva) {
		Reserva reserva = reservaRepository.findById(idReserva)
				.orElseThrow(() -> new IdReservaNaoEncontradoException(idReserva));

		if (reserva.getPagamento() != null
				&& reserva.getPagamento().getStatus() == StatusPagamento.PAGO) {

			reserva.getPagamento().setStatus(StatusPagamento.ESTORNADO);
			reserva.getPagamento().setFormaEscolhida(null);

			reservaRepository.save(reserva);
		} else {
			throw new StatusPagamentoException("Não é possível realizar o estorno para esta reserva, pois ela não está no status PAGO.");
		}
	}
}
