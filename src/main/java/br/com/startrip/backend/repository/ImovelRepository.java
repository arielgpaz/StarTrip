package br.com.startrip.backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.startrip.backend.domain.Imovel;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

	Page<Imovel> findByProprietarioIdEqualsAndDeletedIs(Pageable pageable, Long idProprietario, boolean deleted);

	Optional<Imovel> findByIdAndDeletedIs(Long id, boolean deleted);

	Page<Imovel> findByDeletedIs(boolean deleted, Pageable pageable);

}
