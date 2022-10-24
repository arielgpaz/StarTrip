package br.com.startrip.backend.service.reserva;

import br.com.startrip.backend.controller.request.CadastrarReservaRequest;
import br.com.startrip.backend.controller.response.DadosAnuncioResponse;
import br.com.startrip.backend.controller.response.DadosSolicitanteResponse;
import br.com.startrip.backend.controller.response.InformacaoReservaResponse;
import br.com.startrip.backend.domain.*;
import br.com.startrip.backend.exception.anuncio.IdAnuncioNaoEncontradoException;
import br.com.startrip.backend.exception.reserva.*;
import br.com.startrip.backend.exception.usuario.IdUsuarioInexistenteException;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ReservaRepository;
import br.com.startrip.backend.repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public InformacaoReservaResponse realizarUmaReserva(CadastrarReservaRequest cadastrarReservaRequest) {
        Usuario solicitante = this.buscarSolicitante(cadastrarReservaRequest);
        Anuncio anuncio = this.buscarAnuncio(cadastrarReservaRequest);

        LocalDate dataInicial = cadastrarReservaRequest.getPeriodo().getDataHoraInicial().toLocalDate();
        LocalDate dataFinal = cadastrarReservaRequest.getPeriodo().getDataHoraFinal().toLocalDate();

        this.verificarDisponibilidadeDoEstabelecimento(anuncio, dataInicial, dataFinal);

        LocalDateTime dataHoraInicioReserva = LocalDateTime.of(dataInicial, LocalTime.parse("14:00"));
        LocalDateTime dataHoraFimReserva = LocalDateTime.of(dataFinal, LocalTime.parse("12:00"));
        Periodo periodo = new Periodo(dataHoraInicioReserva, dataHoraFimReserva);

        long quantidadeDiarias = this.calcularQuantidadeDiarias(dataInicial, dataFinal);
        Pagamento pagamento = this.definirPagamento(anuncio.getValorDiaria(), quantidadeDiarias);

        this.avaliarCondicoesMinimasDosEstabelecimentos(cadastrarReservaRequest, anuncio, quantidadeDiarias);

        Reserva reserva = this.salvarReserva(cadastrarReservaRequest, solicitante, anuncio, periodo, pagamento);

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

    private Pagamento definirPagamento(BigDecimal valorDiaria, long quantidadeDiarias) {
        return Pagamento.builder()
                .valorTotal(valorDiaria.multiply(BigDecimal.valueOf(quantidadeDiarias)))
                .status(StatusPagamento.PENDENTE)
                .build();
    }

    private void avaliarCondicoesMinimasDosEstabelecimentos(CadastrarReservaRequest cadastrarReservaRequest, Anuncio anuncio, long quantidadeDiarias) {

        if (anuncio.getImovel() != null
                && anuncio.getImovel().getTipoImovel() != null
                && TipoImovel.HOTEL.equals(anuncio.getImovel().getTipoImovel())
                && cadastrarReservaRequest.getQuantidadePessoas() < 2) {
            throw new MinimoDuasPessoasParaHotelException();
        }

        if (anuncio.getImovel() != null
                && anuncio.getImovel().getTipoImovel() != null
                && TipoImovel.POUSADA.equals(anuncio.getImovel().getTipoImovel())
                && quantidadeDiarias < 5) {
            throw new MinimoDeDiariasParaPousadaException();
        }
    }

    private Anuncio buscarAnuncio(CadastrarReservaRequest cadastrarReservaRequest) {

        Anuncio anuncio = anuncioRepository.findById(cadastrarReservaRequest.getIdAnuncio())
                .orElseThrow(() -> new IdAnuncioNaoEncontradoException(cadastrarReservaRequest.getIdAnuncio()));

        if (anuncio.getAnunciante() != null
                && cadastrarReservaRequest.getIdSolicitante().equals(anuncio.getAnunciante().getId())) {
            throw new SolicitanteMesmoAnuncianteException();
        }

        return anuncio;
    }

    private Usuario buscarSolicitante(CadastrarReservaRequest cadastrarReservaRequest) {
        return usuarioRepository.findById(cadastrarReservaRequest.getIdSolicitante())
                .orElseThrow(() -> new IdUsuarioInexistenteException(cadastrarReservaRequest.getIdSolicitante()));
    }

    private void verificarDisponibilidadeDoEstabelecimento(Anuncio anuncio, LocalDate dataInicial, LocalDate dataFinal) {

        List<Reserva> reservasExistentes = reservaRepository.findByAnuncioId(anuncio.getId());

        for (Reserva reserva : reservasExistentes) {

            LocalDate dataInicioReservaExistente = reserva.getPeriodo().getDataHoraInicial().toLocalDate();
            LocalDate dataFinalReservaExistente = reserva.getPeriodo().getDataHoraFinal().toLocalDate();

            if ((dataInicial.isBefore(dataFinalReservaExistente) && dataFinal.isAfter(dataInicioReservaExistente))
                    && (reserva.getPagamento().getStatus().equals(StatusPagamento.PAGO) ||
                    reserva.getPagamento().getStatus().equals(StatusPagamento.PENDENTE))) {
                throw new AnuncioJaReservadoException();
            }
        }
    }

    private long calcularQuantidadeDiarias(LocalDate dataInicial, LocalDate dataFinal) {

        if (dataInicial.isAfter(dataFinal)) {
            throw new DataFinalMenorException();
        }

        long quantidadeDiarias = ChronoUnit.DAYS.between(dataInicial, dataFinal);

        if (quantidadeDiarias < 1) {
            throw new NumeroMinimoDiariasException();
        }

        return quantidadeDiarias;
    }
}
