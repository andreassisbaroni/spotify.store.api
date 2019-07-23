package br.com.baroni.spotify.store.api.application.query;

public class GenreQuery {
    String description;

    public GenreQuery(String description) {
        this.setDescription(description);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
