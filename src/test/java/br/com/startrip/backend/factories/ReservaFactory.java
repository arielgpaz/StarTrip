package br.com.startrip.backend.factories;

import java.time.LocalDateTime;

import br.com.startrip.backend.domain.Periodo;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;

public class ReservaFactory {
	public static Reserva criaReserva(StatusPagamento statusPagamento) {
		return Reserva.builder()
				.id(2L)
				.solicitante(UsuarioFactory.criaUsuarioSolicitante())
				.anuncio(AnuncioFactory.criaAnuncio())
				.quantidadePessoas(3)
				.periodo(PeriodoFactory.criaPeriodo())
				.pagamento(PagamentoFactory.criaPagamento(statusPagamento))
				.dataHoraReserva(LocalDateTime.now())
				.build();
	}
}
