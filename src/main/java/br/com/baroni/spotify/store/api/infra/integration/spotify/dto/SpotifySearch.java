package br.com.baroni.spotify.store.api.infra.integration.spotify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifySearch {

    @JsonProperty("albums")
    private SpotifyAlbums albums;

    public SpotifySearch() {
    }

    public SpotifyAlbums getAlbums() {
        return albums;
    }

    public void setAlbums(SpotifyAlbums albums) {
        this.albums = albums;
    }
}
