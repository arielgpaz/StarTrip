package br.com.startrip.backend.service.reserva;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.startrip.backend.exception.anuncio.IdAnuncioNaoEncontradoException;
import br.com.startrip.backend.exception.reserva.MinimoDuasPessoasParaHotelException;
import br.com.startrip.backend.exception.reserva.NumeroMinimoDiariasException;
import br.com.startrip.backend.exception.reserva.SolicitanteMesmoAnuncianteException;
import br.com.startrip.backend.controller.request.CadastrarReservaRequest;
import br.com.startrip.backend.controller.response.DadosAnuncioResponse;
import br.com.startrip.backend.controller.response.DadosSolicitanteResponse;
import br.com.startrip.backend.controller.response.InformacaoReservaResponse;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.Pagamento;
import br.com.startrip.backend.domain.Periodo;
import br.com.startrip.backend.domain.Reserva;
import br.com.startrip.backend.domain.StatusPagamento;
import br.com.startrip.backend.domain.TipoImovel;
import br.com.startrip.backend.domain.Usuario;
import br.com.startrip.backend.exception.reserva.AnuncioJaReservadoException;
import br.com.startrip.backend.exception.reserva.DataFinalMenorException;
import br.com.startrip.backend.exception.reserva.MinimoDeDiariasParaPousadaException;
import br.com.startrip.backend.exception.usuario.IdUsuarioInexistenteException;
import br.com.startrip.backend.repository.AnuncioRepository;
import br.com.startrip.backend.repository.ReservaRepository;
import br.com.startrip.backend.repository.UsuarioRepository;

@Service
public class RealizarReservaService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AnuncioRepository anuncioRepository;

	@Autowired
	private ReservaRepository reservaRepository;

	public InformacaoReservaResponse realizarUmaReserva(CadastrarReservaRequest cadastrarReservaRequest) {
		Usuario solicitante = buscarSolicitante(cadastrarReservaRequest);
		Anuncio anuncio = buscarAnuncio(cadastrarReservaRequest);

		LocalDate dataInicial = cadastrarReservaRequest.getPeriodo()
				.getDataHoraInicial()
				.toLocalDate();
		LocalDate dataFinal = cadastrarReservaRequest.getPeriodo()
				.getDataHoraFinal()
				.toLocalDate();
		long quantidadeDiarias = calcularQuantidadeDiarias(dataInicial, dataFinal);

		verificarDisponibilidadeDoEstabelecimento(anuncio, dataInicial, dataFinal);

		LocalDateTime dataHoraInicioReserva = LocalDateTime.of(dataInicial, LocalTime.parse("14:00"));
		LocalDateTime dataHoraFimReserva = LocalDateTime.of(dataFinal, LocalTime.parse("12:00"));
		Periodo periodo = new Periodo(dataHoraInicioReserva, dataHoraFimReserva);

		Pagamento pagamento = definirPagamento(anuncio, quantidadeDiarias);

		avaliarCondicoesMinimasDosEstabelecimentos(cadastrarReservaRequest, anuncio, quantidadeDiarias);

		Reserva reserva = salvarReserva(cadastrarReservaRequest, solicitante, anuncio, periodo, pagamento);

		DadosSolicitanteResponse dadosSolicitanteResponse = new DadosSolicitanteResponse(solicitante.getId(), solicitante.getNome());

		DadosAnuncioResponse dadosAnuncioResponse = new DadosAnuncioResponse(anuncio.getId(), anuncio.getImovel(), anuncio.getAnunciante(), anuncio.getFormasAceitas(), anuncio.getDescricao());

		return new InformacaoReservaResponse(reserva.getId(), dadosSolicitanteResponse, cadastrarReservaRequest.getQuantidadePessoas(), dadosAnuncioResponse, periodo, pagamento);
	}

	private Reserva salvarReserva(CadastrarReservaRequest cadastrarReservaRequest, Usuario solicitante, Anuncio anuncio, Periodo periodo, Pagamento pagamento) {
		Reserva reserva = new Reserva(null, solicitante, anuncio, periodo, cadastrarReservaRequest.getQuantidadePessoas(), LocalDateTime.now(), pagamento);

		reservaRepository.save(reserva);

		return reserva;
	}

	private Pagamento definirPagamento(Anuncio anuncio, long quantidadeDiarias) {
		BigDecimal valorTotal = anuncio.getValorDiaria()
				.multiply(BigDecimal.valueOf(quantidadeDiarias));

		return new Pagamento(valorTotal, null, StatusPagamento.PENDENTE);
	}

	private void avaliarCondicoesMinimasDosEstabelecimentos(CadastrarReservaRequest cadastrarReservaRequest, Anuncio anuncio, long quantidadeDiarias) {
		if (anuncio.getImovel() != null && anuncio.getImovel()
				.getTipoImovel() != null && TipoImovel.HOTEL.equals(anuncio.getImovel()
				.getTipoImovel()) && cadastrarReservaRequest.getQuantidadePessoas() < 2) {
			throw new MinimoDuasPessoasParaHotelException();
		}

		if (anuncio.getImovel() != null && anuncio.getImovel()
				.getTipoImovel() != null && TipoImovel.POUSADA.equals(anuncio.getImovel()
				.getTipoImovel()) && quantidadeDiarias < 5) {
			throw new MinimoDeDiariasParaPousadaException();
		}
	}

	private Anuncio buscarAnuncio(CadastrarReservaRequest cadastrarReservaRequest) {
		Anuncio anuncio = anuncioRepository.findById(cadastrarReservaRequest.getIdAnuncio())
				.orElseThrow(() -> new IdAnuncioNaoEncontradoException(cadastrarReservaRequest.getIdAnuncio()));

		if (anuncio.getAnunciante() != null && cadastrarReservaRequest.getIdSolicitante()
				.equals(anuncio.getAnunciante()
						.getId())) {
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
			LocalDate dataInicioReservaExistente = reserva.getPeriodo()
					.getDataHoraInicial()
					.toLocalDate();
			LocalDate dataFinalReservaExistente = reserva.getPeriodo()
					.getDataHoraFinal()
					.toLocalDate();

			if ((dataInicial.isBefore(dataFinalReservaExistente) && dataFinal.isAfter(dataInicioReservaExistente)) && (reserva.getPagamento()
					.getStatus()
					.equals(StatusPagamento.PAGO) || reserva.getPagamento()
					.getStatus()
					.equals(StatusPagamento.PENDENTE))) {
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
