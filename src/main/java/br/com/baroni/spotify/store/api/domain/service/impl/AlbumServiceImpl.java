package br.com.baroni.spotify.store.api.domain.service.impl;

import br.com.baroni.spotify.store.api.application.query.AlbumQuery;
import br.com.baroni.spotify.store.api.domain.entity.Album;
import br.com.baroni.spotify.store.api.domain.exception.EntityNotFoundException;
import br.com.baroni.spotify.store.api.domain.service.AlbumService;
import br.com.baroni.spotify.store.api.infra.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlbumServiceImpl implements AlbumService {

    private AlbumRepository repository;

    @Autowired
    public AlbumServiceImpl(AlbumRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<AlbumQuery> findByGenreDescription(String genreDescription, Pageable pageable) {
        Page<Album> albumPage = this.repository.findAllByGenre_DescriptionContainingIgnoreCaseOrderByNameAsc(genreDescription, pageable);

        return new PageImpl<>(albumPage.getContent().parallelStream().map(album ->
                new AlbumQuery(album.getId(), album.getArtist(), album.getName(), album.getGenreDescription(), album.getPrice())
        ).collect(Collectors.toList()));
    }

    @Override
    public AlbumQuery findById(UUID id) {
        Album album = this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Album.class));
        return new AlbumQuery(album.getId(), album.getArtist(), album.getName(), album.getGenreDescription(), album.getPrice());
    }

}
