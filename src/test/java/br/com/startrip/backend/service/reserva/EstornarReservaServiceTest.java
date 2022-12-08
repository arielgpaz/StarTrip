package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.exception.reserva.IdReservaNaoEncontradoException;
import br.com.startrip.backend.exception.reserva.StatusPagamentoException;
import br.com.startrip.backend.factories.ReservaFactory;
import br.com.startrip.backend.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class EstornarReservaServiceTest {

    @InjectMocks
    private EstornarReservaService service;

    @Mock
    private ReservaRepository reservaRepository;

    Reserva reserva;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reserva = ReservaFactory.criaReserva(StatusPagamento.PAGO);
    }

    @Test
    void givenIdReservaValido_whenEstornarReserva_thenCancelarReservaComSucesso() {
        when(reservaRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(reserva));
        when(reservaRepository.save(any()))
                .thenReturn(reserva);

        service.estornarReserva(1L);

        assertEquals(StatusPagamento.ESTORNADO, reserva.getPagamento().getStatus());
        assertNull(reserva.getPagamento().getFormaEscolhida());
    }

    @Test
    void givenIdReservaInexistente_whenEstornarReserva_thenThrowsIdReservaNaoEncontradoException() {
        when(reservaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(IdReservaNaoEncontradoException.class, () -> service.estornarReserva(1L));
    }

    @Test
    void givenIdReservaComStatusDiferenteDePAGO_whenEstornarReserva_thenThrowsStatusPagamentoException() {
        reserva.getPagamento().setStatus(StatusPagamento.CANCELADO);

        when(reservaRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(reserva));

        assertThrows(StatusPagamentoException.class, () -> service.estornarReserva(1L));
    }
}
