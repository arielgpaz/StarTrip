package br.com.startrip.backend.integration.fixture;

import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.TipoImovel;

import java.util.Arrays;

public class ImovelFixture {

    private Imovel.ImovelBuilder builder = Imovel.builder();

    public static ImovelFixture get() {
        return new ImovelFixture();
    }

    public Imovel build() {
        return builder.build();
    }

    public ImovelFixture valido() {
        return comIdentificacao()
        .comTipoImovel()
        .comEndereco()
        .comProprietario()
        .comCaracteristicas();
    }

    public ImovelFixture comCaracteristicas() {
        builder.caracteristicas(Arrays.asList(CaracteristicaImovelFixture.get().valido().build()));
        return this;
    }

    public ImovelFixture comProprietario() {
        builder.proprietario(UsuarioFixture.get().valido().build());
        return this;
    }

    public ImovelFixture comEndereco() {
        builder.endereco(EnderecoFixture.get().valido().build());
        return this;
    }

    public ImovelFixture comTipoImovel() {
        builder.tipoImovel(TipoImovel.CASA);
        return this;
    }

    public ImovelFixture comIdentificacao() {
        builder.identificacao("Identificação fixture");
        return this;
    }

}
