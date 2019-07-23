package br.com.baroni.spotify.store.api.infra.repository;


import br.com.baroni.spotify.store.api.domain.entity.SpotifyIntegrationLock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface SpotifyIntegrationLockRepository extends JpaRepository<SpotifyIntegrationLock, UUID> {

    Collection<SpotifyIntegrationLock> findAllBySuccess(Boolean success);
}
