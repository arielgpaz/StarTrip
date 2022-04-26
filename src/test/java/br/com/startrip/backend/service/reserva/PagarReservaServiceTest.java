package br.com.startrip.backend.service.reserva;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.exception.reserva.FormaPagamentoException;
import br.com.startrip.backend.factories.ReservaFactory;
import br.com.startrip.backend.repository.ReservaRepository;

class PagarReservaServiceTest {

	@InjectMocks
	private PagarReservaService service;

	@Mock
	private ReservaRepository reservaRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void givenReservaComPagamentoPendente_whenPagarReserva_thenReturnReservaComPagamentoRealizado() {
		Reserva reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);

		Mockito.when(reservaRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(reserva));
		Mockito.when(reservaRepository.save(Mockito.any(Reserva.class))).thenReturn(reserva);

		Reserva response = service.pagarReserva(2L, FormaPagamento.DINHEIRO);

		Assertions.assertEquals(StatusPagamento.PAGO, response.getPagamento().getStatus());
		Assertions.assertEquals(FormaPagamento.DINHEIRO, response.getPagamento().getFormaEscolhida());
		Mockito.verify(reservaRepository).findById(Mockito.anyLong());
		Mockito.verify(reservaRepository).save(Mockito.any(Reserva.class));
	}

	@Test()
	void givenReservaQueNaoAceitaPIX_whenPagarReservaComPix_thenReturnFormaPagamentoException() {
		Reserva reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);

		Mockito.when(reservaRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(reserva));

		Assertions.assertThrows(FormaPagamentoException.class, () -> service.pagarReserva(2L, FormaPagamento.PIX));
	}
}