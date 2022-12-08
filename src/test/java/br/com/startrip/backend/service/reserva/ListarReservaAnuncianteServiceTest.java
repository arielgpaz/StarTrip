package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
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
import static org.mockito.Mockito.*;

class ListarReservaAnuncianteServiceTest {

    @InjectMocks
    private ListarReservaAnuncianteService service;

    @Mock
    private ReservaRepository reservaRepository;

    Reserva reserva;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);
    }

    @Test
    void givenIdAnuncianteValido_whenListarReservaDeAnunciante_thenReturnReservas() {
        when(reservaRepository.findByAnuncioAnuncianteId(any(), anyLong()))
                .thenReturn(new PageImpl<>(List.of(reserva)));

        Page<Reserva> response = service.listarReservaDeAnunciante(PageRequest.of(0, 5), 1L);

        assertNotNull(response);
        assertEquals(1, response.getSize());
    }
}
