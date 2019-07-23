package br.com.baroni.spotify.store.api.infra.integration.spotify.impl;

import br.com.baroni.spotify.store.api.domain.entity.Album;
import br.com.baroni.spotify.store.api.domain.entity.Genre;
import br.com.baroni.spotify.store.api.infra.integration.spotify.SpotifyIntegration;
import br.com.baroni.spotify.store.api.infra.integration.spotify.dto.SpotifyAlbum;
import br.com.baroni.spotify.store.api.infra.integration.spotify.dto.SpotifySearch;
import br.com.baroni.spotify.store.api.infra.integration.spotify.dto.SpotifyToken;
import br.com.baroni.spotify.store.api.infra.repository.AlbumRepository;
import br.com.baroni.spotify.store.api.infra.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Objects;

@Profile("prod")
@Service
public class SpotifyIntegrationImpl implements SpotifyIntegration {

    @Value("${spotify.integration.auth.url}")
    private String urlSpotifyAuth;

    @Value("${spotify.integration.search.url}")
    private String urlSpotifySearch;

    @Value("${spotify.integration.client.id.base64}")
    private String clientIdBase64;

    private final GenreRepository genreRepository;
    private final AlbumRepository albumRepository;

    @Autowired
    public SpotifyIntegrationImpl(GenreRepository genreRepository, AlbumRepository albumRepository) {
        this.genreRepository = genreRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    @Transactional
    public void integrate() {
        SpotifyToken spotifyToken = this.getTokenAuthorization();

        Collection<Genre> genres = this.genreRepository.findAll();
        if (!CollectionUtils.isEmpty(genres)) {
            genres.parallelStream().forEach(
                    genre -> this.getAlbums(spotifyToken, genre)
            );
        }
    }

    private SpotifyToken getTokenAuthorization() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + this.clientIdBase64);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<SpotifyToken> responseEntity = new RestTemplate().exchange(
                urlSpotifyAuth,
                HttpMethod.POST,
                httpEntity,
                SpotifyToken.class
        );

        return responseEntity.getBody();
    }

    private void getAlbums(SpotifyToken token, Genre genre) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token.getAccessToken());

        HttpEntity<String> httpEntity = new HttpEntity<>("headers", httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<SpotifySearch> response = restTemplate.exchange(urlSpotifySearch + "&q=" + genre.getDescription(),
                HttpMethod.GET,
                httpEntity,
                SpotifySearch.class);

        if (response.getStatusCode().equals(HttpStatus.OK) && !Objects.isNull(response.getBody())) {
            response.getBody()
                    .getAlbums()
                    .getAlbums()
                    .parallelStream()
                    .forEach(
                            album -> this.saveAlbum(genre, album)
                    );
        }
    }

    private void saveAlbum(Genre genre, SpotifyAlbum spotifyAlbum) {
        Album album = new Album(spotifyAlbum.getFirstArtistName(), spotifyAlbum.getName(), genre);
        this.albumRepository.save(album);
    }

}
