package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.controller.request.CadastrarReservaRequest;
import br.com.startrip.backend.controller.response.DadosAnuncioResponse;
import br.com.startrip.backend.controller.response.DadosSolicitanteResponse;
import br.com.startrip.backend.controller.response.InformacaoReservaResponse;
import br.com.startrip.backend.domain.*;
import br.com.startrip.backend.exception.reserva.*;
import br.com.startrip.backend.repository.ReservaRepository;
import br.com.startrip.backend.service.anuncio.AnuncioService;
import br.com.startrip.backend.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RealizarReservaService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AnuncioService anuncioService;

    @Autowired
    private ReservaRepository reservaRepository;

    public InformacaoReservaResponse realizarUmaReserva(CadastrarReservaRequest cadastrarReservaRequest) {
        Usuario solicitante = usuarioService.buscarUsuarioPorId(cadastrarReservaRequest.getIdSolicitante());
        Anuncio anuncio = anuncioService.buscarAnuncioPorId(cadastrarReservaRequest.getIdAnuncio());

        if (anuncio.getAnunciante() != null
                && solicitante.getId().equals(anuncio.getAnunciante().getId())) {
            throw new SolicitanteMesmoAnuncianteException();
        }

        LocalDate dataInicialRequest = cadastrarReservaRequest.getPeriodo().getDataHoraInicial().toLocalDate();
        LocalDate dataFinalRequest = cadastrarReservaRequest.getPeriodo().getDataHoraFinal().toLocalDate();

        this.verificarDisponibilidadeDoEstabelecimento(anuncio.getId(), dataInicialRequest, dataFinalRequest);

        LocalDateTime dataHoraInicioReserva = LocalDateTime.of(dataInicialRequest, LocalTime.parse("14:00"));
        LocalDateTime dataHoraFimReserva = LocalDateTime.of(dataFinalRequest, LocalTime.parse("12:00"));
        Periodo periodo = new Periodo(dataHoraInicioReserva, dataHoraFimReserva);

        long quantidadeDiarias = this.calcularQuantidadeDiarias(dataInicialRequest, dataFinalRequest);
        Pagamento pagamento = this.definirPagamento(anuncio.getValorDiaria(), quantidadeDiarias);

        this.avaliarCondicoesMinimasDosEstabelecimentos(cadastrarReservaRequest.getQuantidadePessoas(), anuncio.getImovel(), quantidadeDiarias);

        Reserva reserva = this.salvarReserva(cadastrarReservaRequest, solicitante, anuncio, periodo, pagamento);

        return this.construirReservaResponse(cadastrarReservaRequest, solicitante, anuncio, periodo, pagamento, reserva);
    }

    private InformacaoReservaResponse construirReservaResponse(CadastrarReservaRequest cadastrarReservaRequest, Usuario solicitante, Anuncio anuncio, Periodo periodo, Pagamento pagamento, Reserva reserva) {
        DadosSolicitanteResponse dadosSolicitanteResponse = DadosSolicitanteResponse.builder()
                .id(solicitante.getId())
                .nome(solicitante.getNome())
                .build();

        DadosAnuncioResponse dadosAnuncioResponse = DadosAnuncioResponse.builder()
                .id(anuncio.getId())
                .imovel(anuncio.getImovel())
                .anunciante(anuncio.getAnunciante())
                .formasAceitas(anuncio.getFormasAceitas())
                .descricao(anuncio.getDescricao())
                .build();

        return InformacaoReservaResponse.builder()
                .idReserva(reserva.getId())
                .solicitante(dadosSolicitanteResponse)
                .quantidadePessoas(cadastrarReservaRequest.getQuantidadePessoas())
                .anuncio(dadosAnuncioResponse)
                .periodo(periodo)
                .pagamento(pagamento)
                .build();
    }

    private Reserva salvarReserva(CadastrarReservaRequest cadastrarReservaRequest, Usuario solicitante, Anuncio anuncio, Periodo periodo, Pagamento pagamento) {
        return reservaRepository.save(Reserva.builder()
                .solicitante(solicitante)
                .anuncio(anuncio)
                .periodo(periodo)
                .quantidadePessoas(cadastrarReservaRequest.getQuantidadePessoas())
                .dataHoraReserva(LocalDateTime.now())
                .pagamento(pagamento)
                .build());
    }

    private void avaliarCondicoesMinimasDosEstabelecimentos(Integer quantidadePessoas, Imovel imovel, long quantidadeDiarias) {

        if (imovel != null
                && imovel.getTipoImovel() != null
                && TipoImovel.HOTEL.equals(imovel.getTipoImovel())
                && quantidadePessoas < 2) {
            throw new MinimoDuasPessoasParaHotelException();
        }

        if (imovel != null
                && imovel.getTipoImovel() != null
                && TipoImovel.POUSADA.equals(imovel.getTipoImovel())
                && quantidadeDiarias < 5) {
            throw new MinimoDeDiariasParaPousadaException();
        }
    }

    private Pagamento definirPagamento(BigDecimal valorDiaria, long quantidadeDiarias) {
        return Pagamento.builder()
                .valorTotal(valorDiaria.multiply(BigDecimal.valueOf(quantidadeDiarias)))
                .status(StatusPagamento.PENDENTE)
                .build();
    }

    private long calcularQuantidadeDiarias(LocalDate dataInicialRequest, LocalDate dataFinalRequest) {

        if (dataInicialRequest.isAfter(dataFinalRequest)) {
            throw new DataFinalMenorException();
        }

        long quantidadeDiarias = ChronoUnit.DAYS.between(dataInicialRequest, dataFinalRequest);

        if (quantidadeDiarias < 1) {
            throw new NumeroMinimoDiariasException();
        }

        return quantidadeDiarias;
    }

    private void verificarDisponibilidadeDoEstabelecimento(Long idAnuncio, LocalDate dataInicialRequest, LocalDate dataFinalRequest) {

        List<Reserva> reservasExistentes = reservaRepository.findByAnuncioId(idAnuncio);

        for (Reserva reserva : reservasExistentes) {

            LocalDate dataInicioReservaExistente = reserva.getPeriodo().getDataHoraInicial().toLocalDate();
            LocalDate dataFinalReservaExistente = reserva.getPeriodo().getDataHoraFinal().toLocalDate();

            if ((dataInicialRequest.isBefore(dataFinalReservaExistente) && dataFinalRequest.isAfter(dataInicioReservaExistente))
                    && (reserva.getPagamento().getStatus().equals(StatusPagamento.PAGO) ||
                    reserva.getPagamento().getStatus().equals(StatusPagamento.PENDENTE))) {
                throw new AnuncioJaReservadoException();
            }
        }
    }
}
