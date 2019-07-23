package br.com.baroni.spotify.store.api.domain.service;

import br.com.baroni.spotify.store.api.application.query.AlbumQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AlbumService {

    Page<AlbumQuery> findByGenreDescription(String genreDescription, Pageable pageable);

    AlbumQuery findById(UUID id);
}
