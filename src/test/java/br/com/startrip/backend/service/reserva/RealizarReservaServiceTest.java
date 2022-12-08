package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.controller.request.CadastrarReservaRequest;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.factories.AnuncioFactory;
import br.com.startrip.backend.factories.ReservaFactory;
import br.com.startrip.backend.factories.UsuarioFactory;
import br.com.startrip.backend.repository.ReservaRepository;
import br.com.startrip.backend.service.anuncio.AnuncioService;
import br.com.startrip.backend.service.usuario.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

class RealizarReservaServiceTest {

    @InjectMocks
    private RealizarReservaService service;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private AnuncioService anuncioService;

    private Reserva reserva;
    private CadastrarReservaRequest request;
    private Usuario usuario;
    private Anuncio anuncio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reserva = ReservaFactory.criaReserva(StatusPagamento.PENDENTE);
        request = ReservaFactory.criaReservaRequest();
        usuario = UsuarioFactory.criaUsuarioSolicitante();
        anuncio = AnuncioFactory.criaAnuncio();
    }

    @Test
    void realizarUmaReserva() {
        when(usuarioService.buscarUsuarioPorId(anyLong()))
                .thenReturn(usuario);
        when(anuncioService.buscarAnuncioPorId(anyLong()))
                .thenReturn(anuncio);
        when(reservaRepository.findByAnuncioId(anyLong()))
                .thenReturn(List.of(reserva));
        when(reservaRepository.save(any()))
                .thenReturn(reserva);

        var response = service.realizarUmaReserva(ReservaFactory.criaReservaRequest());
    }
}
