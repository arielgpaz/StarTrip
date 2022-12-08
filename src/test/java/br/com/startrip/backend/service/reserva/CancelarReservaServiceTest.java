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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CancelarReservaServiceTest {

    @InjectMocks
    private CancelarReservaService service;

    @Mock
    private ReservaRepository reservaRepository;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);
    }

    @Test
    void givenIdReservaValido_whenCancelarReserva_thenCancelarReservaComSucesso() {
        when(reservaRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(reserva));
        when(reservaRepository.save(any()))
                .thenReturn(reserva);

        service.cancelarReserva(1L);

        assertEquals(StatusPagamento.CANCELADO, reserva.getPagamento().getStatus());
    }

    @Test
    void givenIdReservaInexistente_whenCancelarReserva_thenThrowsIdReservaNaoEncontradoException() {
        when(reservaRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(IdReservaNaoEncontradoException.class, () -> service.cancelarReserva(1L));
    }

    @Test
    void givenIdReservaComStatusDiferenteDePENDENTE_whenCancelarReserva_thenThrowsStatusPagamentoException() {
        reserva.getPagamento().setStatus(StatusPagamento.ESTORNADO);

        when(reservaRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(reserva));

        assertThrows(StatusPagamentoException.class, () -> service.cancelarReserva(1L));
    }
}
