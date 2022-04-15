package br.com.startrip.backend.unity.service.reservaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.startrip.backend.controller.request.CadastrarReservaRequest;
import br.com.startrip.backend.controller.response.DadosAnuncioResponse;
import br.com.startrip.backend.controller.response.DadosSolicitanteResponse;
import br.com.startrip.backend.controller.response.InformacaoReservaResponse;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.CaracteristicaImovel;
import br.com.startrip.backend.domain.Endereco;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Pagamento;
import br.com.startrip.backend.domain.Periodo;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.domain.TipoAnuncio;
import br.com.startrip.backend.domain.TipoImovel;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ReservaRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
import br.com.startrip.backend.service.reserva.RealizarReservaService;

@ExtendWith(MockitoExtension.class)
class RealizarReservaServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private AnuncioRepository anuncioRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private RealizarReservaService realizarReservaService;

    @Test
    void realizarUmaReserva() {
        Periodo periodo = new Periodo(LocalDateTime.of(2021, 1, 1, 14, 0),
                LocalDateTime.of(2021, 1, 10, 12, 0));
        CadastrarReservaRequest cadastrarReservaRequest = new CadastrarReservaRequest(1L, 1L, periodo, 5);

        Usuario solicitante = Usuario.builder()
                .id(cadastrarReservaRequest.getIdSolicitante())
                .nome("Usuário Um")
                .dataNascimento(LocalDate.of(2000, 1, 1))
                .cpf("12345678910")
                .email("teste01@teste.com")
                .endereco(Endereco.builder()
                        .id(1L)
                        .logradouro("Rua 01")
                        .numero("25")
                        .bairro("Centro")
                        .cep("12345-67")
                        .cidade("Pires do Rio")
                        .estado("Goias")
                        .complemento("")
                        .build())
                .build();

        Anuncio anuncio = Anuncio.builder()
                .id(cadastrarReservaRequest.getIdAnuncio())
                .tipoAnuncio(TipoAnuncio.COMPLETO)
                .imovel(Imovel.builder()
                        .id(1L)
                        .identificacao("Hotel maneiro")
                        .tipoImovel(TipoImovel.HOTEL)
                        .endereco(Endereco.builder()
                                .id(1L)
                                .logradouro("Rua 01")
                                .numero("25")
                                .bairro("Centro")
                                .cep("12345-67")
                                .cidade("Pires do Rio")
                                .estado("Goias")
                                .complemento("")
                                .build())
                        .proprietario(Usuario.builder()
                                .id(3L)
                                .nome("Usuário Dois")
                                .dataNascimento(LocalDate.of(2000, 1, 1))
                                .cpf("12345678911")
                                .email("teste02@teste.com")
                                .endereco(Endereco.builder()
                                        .id(1L)
                                        .logradouro("Rua 01")
                                        .numero("25")
                                        .bairro("Centro")
                                        .cep("12345-67")
                                        .cidade("Pires do Rio")
                                        .estado("Goias")
                                        .complemento("")
                                        .build())
                                .build())
                        .caracteristicas(Collections.singletonList(CaracteristicaImovel.builder()
                                .id(1L)
                                .descricao("2 piscinas")
                                .build()))
                        .deleted(false)
                        .build())
                .valorDiaria(BigDecimal.valueOf(150))
                .build();

        Mockito.when(usuarioRepository.findById(cadastrarReservaRequest.getIdSolicitante()))
                .thenReturn(Optional.of(solicitante));

        Mockito.when(anuncioRepository.findById(cadastrarReservaRequest.getIdAnuncio()))
                .thenReturn(Optional.of(anuncio));

        InformacaoReservaResponse informacaoReservaResponseEsperada = InformacaoReservaResponse.builder()
                .idReserva(1L)
                .solicitante(DadosSolicitanteResponse.builder()
                        .id(1L)
                        .nome("Usuário Um")
                        .build())
                .quantidadePessoas(5)
                .anuncio(DadosAnuncioResponse.builder()
                        .id(1L)
                        .imovel(Imovel.builder()
                                .id(1L)
                                .identificacao("Hotel maneiro")
                                .tipoImovel(TipoImovel.HOTEL)
                                .endereco(Endereco.builder()
                                        .id(1L)
                                        .logradouro("Rua 01")
                                        .numero("25")
                                        .bairro("Centro")
                                        .cep("12345-67")
                                        .cidade("Pires do Rio")
                                        .estado("Goias")
                                        .complemento("")
                                        .build())
                                .proprietario(Usuario.builder()
                                        .id(3L)
                                        .nome("Usuário Dois")
                                        .dataNascimento(LocalDate.of(2000, 1, 1))
                                        .cpf("12345678911")
                                        .email("teste02@teste.com")
                                        .endereco(Endereco.builder()
                                                .id(1L)
                                                .logradouro("Rua 01")
                                                .numero("25")
                                                .bairro("Centro")
                                                .cep("12345-67")
                                                .cidade("Pires do Rio")
                                                .estado("Goias")
                                                .complemento("")
                                                .build())
                                        .build())
                                .caracteristicas(Collections.singletonList(CaracteristicaImovel.builder()
                                        .id(1L)
                                        .descricao("2 piscinas")
                                        .build()))
                                .deleted(false)
                                .build())
                        .anunciante(Usuario.builder()
                                .id(3L)
                                .nome("Usuário Dois")
                                .dataNascimento(LocalDate.of(2000, 1, 1))
                                .cpf("12345678911")
                                .email("teste02@teste.com")
                                .endereco(Endereco.builder()
                                        .id(1L)
                                        .logradouro("Rua 01")
                                        .numero("25")
                                        .bairro("Centro")
                                        .cep("12345-67")
                                        .cidade("Pires do Rio")
                                        .estado("Goias")
                                        .complemento("")
                                        .build())
                                .build())
                        .build())
                .periodo(Periodo.builder()
                        .dataHoraInicial(LocalDateTime.of(periodo.getDataHoraInicial().toLocalDate(), LocalTime.of(14, 0)))
                        .dataHoraFinal(LocalDateTime.of(periodo.getDataHoraFinal().toLocalDate(), LocalTime.of(12, 0)))
                        .build())
                .pagamento(Pagamento.builder()
                        .status(StatusPagamento.PENDENTE)
                        .formaEscolhida(null)
                        .valorTotal(BigDecimal.valueOf(1350))
                        .build())
                .build();

        InformacaoReservaResponse informacaoReservaResponse = realizarReservaService.realizarUmaReserva(cadastrarReservaRequest);

        Assertions.assertNotNull(informacaoReservaResponse);
        Assertions.assertEquals(informacaoReservaResponseEsperada.getSolicitante().getId(), informacaoReservaResponse.getSolicitante().getId());
        Assertions.assertEquals(informacaoReservaResponseEsperada.getQuantidadePessoas(), informacaoReservaResponse.getQuantidadePessoas());
        Assertions.assertEquals(informacaoReservaResponseEsperada.getAnuncio().getId(), informacaoReservaResponse.getAnuncio().getId());
        Assertions.assertEquals(informacaoReservaResponseEsperada.getPeriodo().getDataHoraInicial(), informacaoReservaResponse.getPeriodo().getDataHoraInicial());
        Assertions.assertEquals(informacaoReservaResponseEsperada.getPeriodo().getDataHoraFinal(), informacaoReservaResponse.getPeriodo().getDataHoraFinal());
        Assertions.assertEquals(informacaoReservaResponseEsperada.getPagamento().getStatus(), informacaoReservaResponse.getPagamento().getStatus());
        Assertions.assertEquals(informacaoReservaResponseEsperada.getPagamento().getValorTotal(), informacaoReservaResponse.getPagamento().getValorTotal());
    }

}