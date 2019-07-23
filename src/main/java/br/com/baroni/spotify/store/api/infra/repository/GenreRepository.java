package br.com.baroni.spotify.store.api.infra.repository;

import br.com.baroni.spotify.store.api.domain.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID>, Serializable {
}
