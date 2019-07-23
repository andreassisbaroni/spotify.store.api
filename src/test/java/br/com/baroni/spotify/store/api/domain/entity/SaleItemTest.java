package br.com.baroni.spotify.store.api.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleItemTest {

    @Test
    public void shouldCalculateCashbackWithoutCashbackDiscount() {
        Sale sale = new Sale();
        Album albumRock = new Album("Artist 1", "Album 1", new Genre("ROCK"), 25.25);

        SaleItem saleItem = new SaleItem(sale, albumRock);
        saleItem.calculateCashback();

        Assertions.assertThat(saleItem.getCashback()).isEqualTo(0D);
    }

    @Test
    public void shouldCalculateCashback() {
        Album albumRock = new Album("Artist 1", "Album 1", new Genre("ROCK"), 75.99);
        CashbackDiscount cashbackDiscount = new CashbackDiscount(new Genre("ROCK"), 40.0, DayOfWeek.SUNDAY, true);

        Sale sale = new Sale();

        SaleItem saleItem = new SaleItem(sale, albumRock);
        saleItem.setCashbackDiscount(cashbackDiscount);
        saleItem.calculateCashback();

        Assertions.assertThat(saleItem.getCashback()).isEqualTo(30.40);
    }

}