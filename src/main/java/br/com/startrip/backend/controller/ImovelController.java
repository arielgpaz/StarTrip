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

import br.com.startrip.backend.service.imovel.ImovelService;
import br.com.startrip.backend.controller.request.CadastrarImovelRequest;
import br.com.startrip.backend.domain.Imovel;

@RestController
@RequestMapping("/imoveis")
public class ImovelController {

	@Autowired
	private ImovelService imovelService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Imovel cadastrarImovel(@RequestBody @Valid CadastrarImovelRequest cadastrarImovelRequest) {
		return imovelService.cadastrarImovel(cadastrarImovelRequest);
	}

	@GetMapping
	public Page<Imovel> listarImoveis(@PageableDefault(sort = "identificacao", direction = Sort.Direction.ASC) Pageable pageable) {
		return imovelService.listarImoveis(pageable);
	}

	@GetMapping("/proprietarios/{idProprietario}")
	public Page<Imovel> listarImoveisDeProprietario(@PageableDefault(sort = "identificacao", direction = Sort.Direction.ASC) Pageable pageable, @PathVariable Long idProprietario) {
		return imovelService.listarImoveisDeProprietario(pageable, idProprietario);
	}

	@GetMapping("/{idImovel}")
	public Imovel listarImoveisPorId(@PathVariable Long idImovel) {
		return imovelService.listarImoveisPorId(idImovel);
	}

	@DeleteMapping("/{idImovel}")
	public void excluirImovel(@PathVariable Long idImovel) {
		imovelService.excluirImovel(idImovel);
	}
}
