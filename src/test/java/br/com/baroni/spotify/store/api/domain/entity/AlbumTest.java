package br.com.baroni.spotify.store.api.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlbumTest {

    @Test
    public void shouldGenerateAPriceToAlbum() {
        Album album = new Album("Artist 1", "Album 1", new Genre("ROCK"));

        Assertions.assertThat(album.getPrice()).isNotNull();
        Assertions.assertThat(album.getPrice()).isPositive();
        Assertions.assertThat(album.getPrice()).isBetween(1D, 150D);
    }

}