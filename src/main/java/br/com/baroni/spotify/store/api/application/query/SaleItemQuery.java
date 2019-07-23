package br.com.baroni.spotify.store.api.application.query;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SaleItemQuery {

    @JsonProperty("sale")
    private UUID saleId;

    @JsonProperty("album")
    private String album;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("cashback")
    private Double cashback;

    public SaleItemQuery(UUID saleId, String album, Double price, Double cashback) {
        this.setSaleId(saleId);
        this.setAlbum(album);
        this.setPrice(price);
        this.setCashback(cashback);
    }

    public UUID getSaleId() {
        return saleId;
    }

    public void setSaleId(UUID saleId) {
        this.saleId = saleId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCashback() {
        return cashback;
    }

    public void setCashback(Double cashback) {
        this.cashback = cashback;
    }
}
