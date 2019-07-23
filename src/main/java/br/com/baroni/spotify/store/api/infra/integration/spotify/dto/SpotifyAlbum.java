package br.com.baroni.spotify.store.api.infra.integration.spotify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Objects;

public class SpotifyAlbum {

    @JsonProperty("name")
    private String name;

    @JsonProperty("artists")
    private Collection<SpotifyArtist> artists;

    public SpotifyAlbum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<SpotifyArtist> getArtists() {
        return artists;
    }

    public void setArtists(Collection<SpotifyArtist> artists) {
        this.artists = artists;
    }

    public String getFirstArtistName() {
        if (!CollectionUtils.isEmpty(this.getArtists())) {
            return this.getArtists().iterator().next().getName();
        }
        return "";
    }
}
