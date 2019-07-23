package br.com.baroni.spotify.store.api.application.query;

import java.util.UUID;

public class AlbumQuery {

    private UUID id;
    private String artist;
    private String name;
    private String genre;
    private Double price;

    public AlbumQuery(UUID id, String artist, String name, String genre, Double price) {
        super();
        this.setId(id);
        this.setArtist(artist);
        this.setName(name);
        this.setGenre(genre);
        this.setPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
