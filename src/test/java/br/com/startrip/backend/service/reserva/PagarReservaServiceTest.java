package br.com.startrip.backend.service.reserva;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import br.com.startrip.backend.domain.FormaPagamento;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.exception.reserva.FormaPagamentoException;
import br.com.startrip.backend.factories.ReservaFactory;
import br.com.startrip.backend.repository.ReservaRepository;

class PagarReservaServiceTest {

	@InjectMocks
	@Spy
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

		when(reservaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(reserva));
		when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

		Reserva response = service.pagarReserva(2L, FormaPagamento.DINHEIRO);

		assertEquals(StatusPagamento.PAGO, response.getPagamento()
				.getStatus());
		assertEquals(FormaPagamento.DINHEIRO, response.getPagamento()
				.getFormaEscolhida());
		verify(reservaRepository).findById(anyLong());
		verify(reservaRepository).save(any(Reserva.class));
	}

	@Test()
	void givenReservaQueNaoAceitaPIX_whenPagarReservaComPix_thenReturnFormaPagamentoException() {
		Reserva reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);

		when(reservaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(reserva));

		assertThrows(FormaPagamentoException.class, () -> service.pagarReserva(2L, FormaPagamento.PIX));
	}

//	@Test
//	void givenReservaPara3Pessoas_whenPagarReserva_thenReturnReservaPara1Pessoa() {
//		Reserva reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);
//
//		when(reservaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(reserva));
//		when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
//		doNothing().when(service).alterarQuantidadePessoas(any());
//
//		Reserva response = service.pagarReserva(2L, FormaPagamento.DINHEIRO);
//
//		assertEquals(3, (long) response.getQuantidadePessoas());
//		verify(service).alterarQuantidadePessoas(any());
//	}
}