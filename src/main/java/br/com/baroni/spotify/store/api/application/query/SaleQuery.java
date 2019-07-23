package br.com.baroni.spotify.store.api.application.query;

import br.com.baroni.spotify.store.api.domain.entity.SaleStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class SaleQuery {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("totalPrice")
    private Double totalPrice;

    @JsonProperty("totalCachback")
    private Double totalCashback;

    @JsonProperty("saleDate")
    private LocalDateTime saleDate;

    @JsonProperty("finishDate")
    private LocalDateTime finishDate;

    @JsonProperty("status")
    private SaleStatus saleStatus;

    public SaleQuery(UUID id, LocalDateTime saleDate, SaleStatus status) {
        super();
        this.setId(id);
        this.setSaleDate(saleDate);
        this.setSaleStatus(status);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalCashback() {
        return totalCashback;
    }

    public void setTotalCashback(Double totalCashback) {
        this.totalCashback = totalCashback;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public SaleStatus getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(SaleStatus saleStatus) {
        this.saleStatus = saleStatus;
    }
}
