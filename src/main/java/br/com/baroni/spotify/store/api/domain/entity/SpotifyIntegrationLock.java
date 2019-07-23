package br.com.baroni.spotify.store.api.domain.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Table
@Entity
public class SpotifyIntegrationLock implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column
    private LocalDateTime executionDate;

    @Column
    private Boolean success;

    private SpotifyIntegrationLock() {
        super();
        this.setSuccess(false);
    }

    public SpotifyIntegrationLock(Boolean success) {
        this();
        this.setExecutionDate(LocalDateTime.now());
        this.setSuccess(success);
    }

    public SpotifyIntegrationLock(LocalDateTime executionDate, Boolean success) {
        this();
        this.setExecutionDate(executionDate);
        this.setSuccess(success);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
