package br.com.baroni.spotify.store.api.domain.entity;

import br.com.baroni.spotify.store.api.domain.exception.SaleAlreadyCanceledException;
import br.com.baroni.spotify.store.api.domain.exception.SaleAlreadyConcludedException;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public class Sale implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column
    private LocalDateTime saleDate;

    @Column
    private LocalDateTime finishDate;

    @Column
    private LocalDateTime cancelDate;

    @Column
    private Double totalPrice;

    @Column
    private Double totalCashback;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private Collection<SaleItem> saleItems;

    public Sale() {
        super();
        this.setSaleDate(LocalDateTime.now());
        this.setSaleItems(new ArrayList<>());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDateTime getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDateTime cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Collection<SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(Collection<SaleItem> saleItems) {
        this.saleItems = saleItems;
    }

    public SaleStatus getStatus() {
        if (Objects.nonNull(this.getCancelDate())) {
            return SaleStatus.CANCELED;
        } else if (Objects.nonNull(this.getFinishDate())) {
            return SaleStatus.CONCLUDED;
        }

        return SaleStatus.PENDING;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Double getTotalCashback() {
        return totalCashback;
    }

    public void setTotalCashback(Double totalCashback) {
        this.totalCashback = totalCashback;
    }

    public void calculateTotalPrice() {
        this.setTotalPrice(0D);

        if (!CollectionUtils.isEmpty(this.getSaleItems())) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            this.setTotalPrice(Double.valueOf(
                    decimalFormat.format(
                            this.getSaleItems()
                                    .parallelStream()
                                    .mapToDouble(SaleItem::getPrice)
                                    .sum()).replace(",", ".")
                    )
            );
        }
    }

    public void calculateTotalCashback() {
        this.setTotalCashback(0D);

        if (!CollectionUtils.isEmpty(this.getSaleItems())) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            this.setTotalCashback(Double.valueOf(
                    decimalFormat.format(
                            this.getSaleItems()
                                    .parallelStream()
                                    .mapToDouble(SaleItem::getCashback)
                                    .sum()).replace(",", ".")
                    )
            );
        }
    }

    public void completeSale() {
        if (this.getStatus().equals(SaleStatus.CANCELED)) {
            throw new SaleAlreadyCanceledException();
        } else if (this.getStatus().equals(SaleStatus.CONCLUDED)) {
            throw new SaleAlreadyConcludedException();
        }

        this.setFinishDate(LocalDateTime.now());
    }

    public void cancelSale() {
        if (this.getStatus().equals(SaleStatus.CANCELED)) {
            throw new SaleAlreadyCanceledException();
        }
        this.setCancelDate(LocalDateTime.now());
    }

    public void calculateAggregateFields() {
        this.calculateTotalPrice();
        this.calculateTotalCashback();
    }
}
