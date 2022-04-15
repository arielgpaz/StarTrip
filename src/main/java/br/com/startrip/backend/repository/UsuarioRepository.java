package br.com.startrip.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.startrip.backend.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	boolean existsByEmail(String email);

	boolean existsByEmailAndIdIsNot(String email, Long id);

	Optional<Usuario> findByCpf(String cpf);

	boolean existsByCpf(String cpf);
}
