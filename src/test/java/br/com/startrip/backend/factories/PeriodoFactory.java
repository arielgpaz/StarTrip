package br.com.startrip.backend.factories;

import java.time.LocalDateTime;

import br.com.startrip.backend.domain.Periodo;

public class PeriodoFactory {
	public static Periodo criaPeriodo() {
		return Periodo.builder()
				.dataHoraInicial(LocalDateTime.of(2022, 07, 10, 14, 00))
				.dataHoraFinal(LocalDateTime.of(2022, 07, 15, 12, 00))
				.build();
	}
}
