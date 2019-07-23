package br.com.baroni.spotify.store.api.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

@Entity
@Table
public class Album implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column
    private String artist;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_genre", referencedColumnName = "id")
    private Genre genre;

    @Column(precision = 12, scale = 2)
    private Double price;

    private Album() {
        super();
    }

    public Album(String artist, String name, Genre genre, Double price) {
        this();
        this.setArtist(artist);
        this.setName(name);
        this.setGenre(genre);
        this.setPrice(price);
    }

    public Album(String artist, String name, Genre genre) {
        this();
        this.setArtist(artist);
        this.setName(name);
        this.setGenre(genre);
        this.setPrice(this.generateNewPrice());
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

    public String getGenreDescription() {
        return this.genre.getDescription();
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private Double generateNewPrice() {
        Double minValue = 1D;
        Double maxValue = 150D;
        return minValue + new Random().nextDouble() * (maxValue - minValue);
    }
}
