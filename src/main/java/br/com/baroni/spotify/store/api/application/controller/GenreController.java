package br.com.baroni.spotify.store.api.application.controller;

import br.com.baroni.spotify.store.api.application.query.GenreQuery;
import br.com.baroni.spotify.store.api.domain.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/genres")
public class GenreController implements Serializable {

    private GenreService service;

    @Autowired
    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<GenreQuery>> getAllGenres(Pageable pageable) {
        return new ResponseEntity<>(this.service.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenreQuery> getGenreById(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(this.service.findById(id), HttpStatus.OK);
    }
}
