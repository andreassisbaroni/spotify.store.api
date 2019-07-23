package br.com.baroni.spotify.store.api.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.UUID;

@Table
@Entity
public class SaleItem implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_sale", referencedColumnName = "id")
    private Sale sale;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_album", referencedColumnName = "id")
    private Album album;

    @Column(precision = 12, scale = 2)
    private Double price;

    @Column(precision = 12, scale = 2)
    private Double cashback;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_cashback_discount", referencedColumnName = "id")
    private CashbackDiscount cashbackDiscount;

    private SaleItem() {
        super();
    }

    public SaleItem(Sale sale, Album album) {
        this();
        this.setSale(sale);
        this.setAlbum(album);
        this.setPrice(album.getPrice());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
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

    public CashbackDiscount getCashbackDiscount() {
        return cashbackDiscount;
    }

    public void setCashbackDiscount(CashbackDiscount cashbackDiscount) {
        this.cashbackDiscount = cashbackDiscount;
    }

    public void calculateCashback() {
        this.setCashback(0D);

        if (Objects.nonNull(this.getCashbackDiscount())) {
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            this.setCashback(
                    Double.valueOf(
                            decimalFormat.format(
                                    (this.getPrice() * this.getCashbackDiscount().getPercentage()) / 100).replace(",", ".")
                    ));
        }
    }
}
