package br.com.startrip.backend.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "usuario", url = "https://some-random-api.ml/img/dog")
public interface FotoUsuarioRepository {
    @GetMapping(value = "/")
    String retornaFoto();
}
