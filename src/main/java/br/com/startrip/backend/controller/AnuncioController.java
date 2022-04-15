package br.com.startrip.backend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.startrip.backend.service.anuncio.AnuncioService;
import br.com.startrip.backend.controller.request.CadastrarAnuncioRequest;
import br.com.startrip.backend.domain.Anuncio;

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

	@GetMapping("/anunciantes/{idAnunciante}")
	public Page<Anuncio> listarAnunciosDeAnunciante(@PageableDefault(sort = "valorDiaria", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long idAnunciante) {
		return anuncioService.listarAnunciosDeAnunciante(pageable, idAnunciante);
	}

	@DeleteMapping("/{idAnuncio}")
	public void excluirAnuncio(@PathVariable Long idAnuncio) {
		anuncioService.excluirAnuncio(idAnuncio);
	}
}
