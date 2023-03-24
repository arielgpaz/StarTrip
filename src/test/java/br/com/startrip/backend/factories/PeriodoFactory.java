package br.com.startrip.backend.factories;

import br.com.startrip.backend.domain.Periodo;

import java.time.LocalDateTime;

public class PeriodoFactory {

	public static Periodo criaPeriodoFeriasInverno() {
		return Periodo.builder()
				.dataHoraInicial(LocalDateTime.of(2022, 7, 10, 14, 0))
				.dataHoraFinal(LocalDateTime.of(2022, 7, 15, 12, 0))
				.build();
	}

	public static Periodo criaPeriodoFeriasVerao() {
		return Periodo.builder()
				.dataHoraInicial(LocalDateTime.of(2022, 12, 10, 14, 0))
				.dataHoraFinal(LocalDateTime.of(2022, 12, 15, 12, 0))
				.build();
	}
}
