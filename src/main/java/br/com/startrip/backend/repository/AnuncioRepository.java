package br.com.startrip.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.startrip.backend.domain.Anuncio;
import br.com.startrip.backend.domain.Imovel;
import br.com.startrip.backend.domain.Usuario;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {

	boolean existsByImovelAndDeletedIs(Imovel imovel, boolean deleted);

	Page<Anuncio> findByAnuncianteAndDeletedIs(Pageable pageable, Usuario anunciante, boolean deleted);

	boolean existsByImovelIdAndDeletedIs(Long idImovel, boolean deleted);

	Page<Anuncio> findByDeletedIs(boolean deleted, Pageable pageable);

	Optional<Anuncio> findByIdAndDeletedIs(Long idAnuncio, boolean deleted);
}
