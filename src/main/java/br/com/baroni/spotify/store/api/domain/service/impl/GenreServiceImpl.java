package br.com.baroni.spotify.store.api.domain.service.impl;

import br.com.baroni.spotify.store.api.application.query.GenreQuery;
import br.com.baroni.spotify.store.api.domain.entity.Genre;
import br.com.baroni.spotify.store.api.domain.exception.EntityNotFoundException;
import br.com.baroni.spotify.store.api.domain.service.GenreService;
import br.com.baroni.spotify.store.api.infra.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository repository;

    @Autowired
    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<GenreQuery> findAll(Pageable pageable) {
        Page<Genre> genrePage = this.repository.findAll(pageable);

        List<GenreQuery> genreQueries = genrePage.getContent().parallelStream().map(genre ->
                new GenreQuery(genre.getDescription())
        ).collect(Collectors.toList());

        return new PageImpl<>(genreQueries);
    }

    @Override
    public GenreQuery findById(UUID id) {
        Genre genre = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Genre.class));

        return new GenreQuery(genre.getDescription());
    }
}
