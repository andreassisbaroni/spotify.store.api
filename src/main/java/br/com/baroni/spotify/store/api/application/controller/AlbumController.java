package br.com.baroni.spotify.store.api.application.controller;

import br.com.baroni.spotify.store.api.application.query.AlbumQuery;
import br.com.baroni.spotify.store.api.domain.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/albums")
public class AlbumController implements Serializable {

    private final AlbumService service;

    @Autowired
    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<AlbumQuery>> findAlbumsByGenreDescription(@RequestParam(name = "genre",
            required = false, defaultValue = "") String genreDescription,
                                                                         Pageable pageable) {
        return new ResponseEntity<>(this.service.findByGenreDescription(genreDescription, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumQuery> findById(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(this.service.findById(id), HttpStatus.OK);
    }
}
