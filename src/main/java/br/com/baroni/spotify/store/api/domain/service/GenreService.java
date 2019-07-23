package br.com.baroni.spotify.store.api.domain.service;

import br.com.baroni.spotify.store.api.application.query.GenreQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface GenreService {

    Page<GenreQuery> findAll(Pageable pageable);

    GenreQuery findById(UUID id);
}
