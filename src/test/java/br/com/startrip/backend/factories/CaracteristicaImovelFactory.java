package br.com.startrip.backend.factories;

import br.com.startrip.backend.domain.CaracteristicaImovel;

public class CaracteristicaImovelFactory {
    public static CaracteristicaImovel criaCaracteristicaImovel() {
        return CaracteristicaImovel.builder()
                .id(1L)
                .descricao("Casa de vidro")
                .build();
    }
}
