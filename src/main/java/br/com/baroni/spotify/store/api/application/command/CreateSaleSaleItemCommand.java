package br.com.baroni.spotify.store.api.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateSaleSaleItemCommand {

    @JsonProperty("id")
    @NotNull
    private UUID id;

    public CreateSaleSaleItemCommand() {
        super();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
