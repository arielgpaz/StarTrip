package br.com.startrip.backend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.startrip.backend.service.usuario.UsuarioService;
import br.com.startrip.backend.controller.request.AtualizarUsuarioRequest;
import br.com.startrip.backend.domain.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario cadastrarUsuario(@RequestBody @Valid Usuario usuario) {
		return usuarioService.cadastrar(usuario);
	}

	@GetMapping
	public Page<Usuario> listarUsuarios(@PageableDefault(sort = "nome") Pageable pageable) {
		return usuarioService.listarUsuarios(pageable);
	}

	@GetMapping("/{idUsuario}")
	public Usuario buscarUsuarioPorId(@PathVariable Long idUsuario) {
		return usuarioService.buscarUsuarioPorId(idUsuario);
	}

	@GetMapping("/cpf/{cpf}")
	public Usuario buscarUsuarioPorCpf(@PathVariable String cpf) {
		return usuarioService.buscarUsuarioPorCpf(cpf);
	}

	@PutMapping("/{id}")
	public Usuario alterarUmUsuario(@PathVariable Long id, @RequestBody @Valid AtualizarUsuarioRequest atualizarUsuarioRequest) {
		return usuarioService.alterarUmUsuario(id, atualizarUsuarioRequest);
	}

}
