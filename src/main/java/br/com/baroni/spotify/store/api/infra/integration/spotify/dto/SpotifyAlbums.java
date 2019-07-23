package br.com.baroni.spotify.store.api.infra.integration.spotify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class SpotifyAlbums {

    @JsonProperty("items")
    private Collection<SpotifyAlbum> albums;

    public SpotifyAlbums() {
    }

    public Collection<SpotifyAlbum> getAlbums() {
        return albums;
    }

    public void setAlbums(Collection<SpotifyAlbum> albums) {
        this.albums = albums;
    }
}
