package br.com.baroni.spotify.store.api.application.command;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public class CreateSaleCommand {

    @JsonProperty("albums")
    @NotNull
    Collection<CreateSaleSaleItemCommand> saleItems;

    public CreateSaleCommand() {
        super();
    }

    public Collection<CreateSaleSaleItemCommand> getSaleItems() {
        return saleItems;
    }

    public void getOrderItems(Collection<CreateSaleSaleItemCommand> orderItems) {
        this.saleItems = orderItems;
    }
}
