package br.com.baroni.spotify.store.api.infra.integration.spotify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpotifyArtist {

    @JsonProperty("name")
    private String name;

    public SpotifyArtist() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
