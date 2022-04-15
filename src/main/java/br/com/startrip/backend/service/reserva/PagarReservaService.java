package br.com.startrip.backend.service.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.startrip.backend.exception.reserva.StatusPagamentoException;
import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.exception.reserva.FormaPagamentoException;
import br.com.startrip.backend.exception.reserva.IdReservaNaoEncontradoException;
import br.com.startrip.backend.repository.ReservaRepository;

@Service
public class PagarReservaService {

	@Autowired
	private ReservaRepository reservaRepository;

	public void pagarReserva(final Long idReserva, FormaPagamento formaPagamento) {
		Reserva reserva = reservaRepository.findById(idReserva)
				.orElseThrow(() -> new IdReservaNaoEncontradoException(idReserva));

		if (reserva.getAnuncio() != null && reserva.getAnuncio()
				.getFormasAceitas() != null && !reserva.getAnuncio()
				.getFormasAceitas()
				.contains(formaPagamento)) {
			throw new FormaPagamentoException(formaPagamento, reserva.getAnuncio()
					.getFormasAceitas());
		}

		if (reserva.getPagamento() != null && reserva.getPagamento()
				.getStatus() == StatusPagamento.PENDENTE) {
			reserva.getPagamento()
					.setStatus(StatusPagamento.PAGO);
			reserva.getPagamento()
					.setFormaEscolhida(formaPagamento);
			reservaRepository.save(reserva);
		} else {
			throw new StatusPagamentoException("Não é possível realizar o pagamento para esta reserva, pois ela não está no status PENDENTE.");
		}
	}
}
