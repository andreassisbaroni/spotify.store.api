package br.com.baroni.spotify.store.api.infra.repository;

import br.com.baroni.spotify.store.api.domain.entity.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {

    Page<Album> findAllByGenre_DescriptionContainingIgnoreCaseOrderByNameAsc(String genreDescription, Pageable pageable);
}
