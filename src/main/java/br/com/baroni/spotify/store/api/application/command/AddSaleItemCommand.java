package br.com.baroni.spotify.store.api.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class AddSaleItemCommand {

    @JsonProperty("saleId")
    @NotNull
    private UUID saleId;

    @JsonProperty("album")
    @NotNull
    private UUID albumId;

    private AddSaleItemCommand() {
        super();
    }

    public AddSaleItemCommand(UUID saleId, UUID albumId) {
        this();
        this.setSaleId(saleId);
        this.setAlbumId(albumId);
    }

    public UUID getSaleId() {
        return saleId;
    }

    public void setSaleId(UUID saleId) {
        this.saleId = saleId;
    }

    public UUID getAlbumId() {
        return albumId;
    }

    public void setAlbumId(UUID albumId) {
        this.albumId = albumId;
    }
}
