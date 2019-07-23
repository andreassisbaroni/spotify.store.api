package br.com.baroni.spotify.store.api.infra.inicialization;


import br.com.baroni.spotify.store.api.domain.entity.SpotifyIntegrationLock;
import br.com.baroni.spotify.store.api.infra.integration.spotify.SpotifyIntegration;
import br.com.baroni.spotify.store.api.infra.repository.SpotifyIntegrationLockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Collection;

@Profile("prod")
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final SpotifyIntegration spotifyIntegration;
    private final SpotifyIntegrationLockRepository spotifyIntegrationLockRepository;

    @Autowired
    public ApplicationStartup(SpotifyIntegration spotifyIntegration,
                              SpotifyIntegrationLockRepository spotifyIntegrationLockRepository) {
        this.spotifyIntegration = spotifyIntegration;
        this.spotifyIntegrationLockRepository = spotifyIntegrationLockRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Collection<SpotifyIntegrationLock> executions = this.spotifyIntegrationLockRepository.findAllBySuccess(true);

        if (CollectionUtils.isEmpty(executions)) {
            try {
                this.spotifyIntegration.integrate();
                this.spotifyIntegrationLockRepository.save(new SpotifyIntegrationLock(true));
            } catch (Exception e) {
                this.spotifyIntegrationLockRepository.save(new SpotifyIntegrationLock(false));
            }
        }
    }
}
