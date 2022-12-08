package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.factories.PeriodoFactory;
import br.com.startrip.backend.factories.ReservaFactory;
import br.com.startrip.backend.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListarReservaSolicitanteServiceTest {

    @InjectMocks
    private ListarReservaSolicitanteService service;

    @Mock
    private ReservaRepository reservaRepository;

    Reserva reserva;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);
    }

    @Test
    void givenIdSolicitanteValidoEPeriodo_whenListarReservaDeSolicitante_thenReturnReservas() {
        when(reservaRepository.findBySolicitanteIdAndPeriodoDataHoraInicialBetweenAndPeriodoDataHoraFinalBetween(any(), anyLong(), any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(reserva)));

        Page<Reserva> response = service.listarReservaDeSolicitante(PageRequest.of(0, 5), 1L, PeriodoFactory.criaPeriodo());

        verify(reservaRepository).findBySolicitanteIdAndPeriodoDataHoraInicialBetweenAndPeriodoDataHoraFinalBetween(any(), anyLong(), any(), any(), any(), any());
        assertNotNull(response);
        assertEquals(1, response.getSize());
    }

    @Test
    void givenIdSolicitanteValidoEPeriodoNull_whenListarReservaDeSolicitante_thenReturnReservas() {
        when(reservaRepository.findBySolicitanteId(any(), anyLong()))
                .thenReturn(new PageImpl<>(List.of(reserva)));

        Page<Reserva> response = service.listarReservaDeSolicitante(PageRequest.of(0, 5), 1L, null);

        verify(reservaRepository).findBySolicitanteId(any(), anyLong());
        assertNotNull(response);
        assertEquals(1, response.getSize());
    }
}
