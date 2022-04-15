package br.com.startrip.backend.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "usuario", url = "https://some-random-api.ml/img/dog")
public interface FotoUsuarioRepository {
	@RequestMapping(method = RequestMethod.GET, value = "/")
	String retornaFoto();
}
