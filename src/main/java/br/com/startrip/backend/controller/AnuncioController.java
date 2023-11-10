package br.com.startrip.backend.controller;

import br.com.startrip.backend.controller.request.CadastrarAnuncioRequest;
import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.service.anuncio.AnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

	@Autowired
	private AnuncioService anuncioService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Anuncio anunciarImovel(@RequestBody @Valid CadastrarAnuncioRequest cadastrarAnuncioRequest) {
		return anuncioService.anunciarImovel(cadastrarAnuncioRequest);
	}

	@GetMapping
	public Page<Anuncio> listarAnuncios(@PageableDefault(sort = "valorDiaria", direction = Sort.Direction.ASC) Pageable pageable) {
		return anuncioService.listarAnuncios(pageable);
	}

	@GetMapping("/anunciantes/id/{idAnunciante}")
	public Page<Anuncio> listarAnunciosDeAnunciante(@PageableDefault(sort = "valorDiaria", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long idAnunciante) {
		return anuncioService.listarAnunciosDeAnunciante(pageable, idAnunciante);
	}

	@DeleteMapping("/{idAnuncio}")
	public void excluirAnuncio(@PathVariable Long idAnuncio) {
		anuncioService.excluirAnuncio(idAnuncio);
	}
}
