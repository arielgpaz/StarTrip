package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.controller.request.CadastrarReservaRequest;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.factories.ReservaFactory;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ReservaRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RealizarReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AnuncioRepository anuncioRepository;

    Reserva reserva;
    CadastrarReservaRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);
        request = ReservaFactory.criaReservaRequest();
    }

    @Test
    void realizarUmaReserva() {
    }
}
