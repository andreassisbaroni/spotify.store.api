package br.com.baroni.spotify.store.api.domain.entity;

import br.com.baroni.spotify.store.api.domain.exception.InvalidPercentageException;
import br.com.baroni.spotify.store.api.infra.converter.DayOfWeekConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.UUID;

@Entity
@Table
public class CashbackDiscount implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_genre", referencedColumnName = "id")
    private Genre genre;

    @Column
    private Double percentage;

    @Column
    @Convert(converter = DayOfWeekConverter.class)
    private DayOfWeek dayOfWeek;

    @Column
    private Boolean active;

    private CashbackDiscount() {
        super();
        this.setActive(Boolean.FALSE);
    }

    public CashbackDiscount(Genre genre, Double percentage, DayOfWeek dayOfWeek) {
        this();
        this.setGenre(genre);
        this.setPercentage(percentage);
        this.setDayOfWeek(dayOfWeek);
    }

    public CashbackDiscount(Genre genre, Double percentage, DayOfWeek dayOfWeek, Boolean active) {
        this();
        this.setGenre(genre);
        this.setPercentage(percentage);
        this.setDayOfWeek(dayOfWeek);
        this.setActive(active);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        if (percentage < 0.0 || percentage > 100) {
            throw new InvalidPercentageException();
        }

        this.percentage = percentage;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
