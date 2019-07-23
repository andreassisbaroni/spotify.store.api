package br.com.baroni.spotify.store.api.domain.entity;

import br.com.baroni.spotify.store.api.domain.exception.SaleAlreadyCanceledException;
import br.com.baroni.spotify.store.api.domain.exception.SaleAlreadyConcludedException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.DayOfWeek;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleTest {

    @Test
    public void shouldCalculateTotalPrice() {
        Album albumRock = new Album("Artist 1", "Album 1", new Genre("ROCK"), 15.50);
        Album albumPop = new Album("Artist 1", "Album 1", new Genre("POP"), 18.20);

        Sale sale = new Sale();

        SaleItem firstSaleItem = new SaleItem(sale, albumRock);
        SaleItem secondSaleItem = new SaleItem(sale, albumPop);

        sale.getSaleItems().add(firstSaleItem);
        sale.getSaleItems().add(secondSaleItem);

        sale.calculateTotalPrice();

        Assertions.assertThat(sale.getTotalPrice()).isEqualTo(33.7);
    }

    @Test
    public void shouldCalculateTotalCashBackWithoutCashback() {
        Album albumRock = new Album("Artist 1", "Album 1", new Genre("ROCK"), 20.40);
        Album albumPop = new Album("Artist 1", "Album 1", new Genre("POP"), 17.45);

        Sale sale = new Sale();

        SaleItem firstSaleItem = new SaleItem(sale, albumRock);
        firstSaleItem.calculateCashback();
        SaleItem secondSaleItem = new SaleItem(sale, albumPop);
        secondSaleItem.calculateCashback();

        sale.getSaleItems().add(firstSaleItem);
        sale.getSaleItems().add(secondSaleItem);

        sale.calculateTotalCashback();

        Assertions.assertThat(sale.getTotalCashback()).isEqualTo(0.0);
    }

    @Test
    public void shouldCalculateTotalCashbackWithOneItemWithCashback() {
        Album albumRock = new Album("Artist 1", "Album 1", new Genre("ROCK"), 75.99);
        Album albumPop = new Album("Artist 1", "Album 1", new Genre("POP"), 20.00);
        CashbackDiscount cashbackDiscount = new CashbackDiscount(new Genre("ROCK"), 40.0, DayOfWeek.SUNDAY, true);

        Sale sale = new Sale();

        SaleItem firstSaleItem = new SaleItem(sale, albumRock);
        firstSaleItem.setCashbackDiscount(cashbackDiscount);
        firstSaleItem.calculateCashback();
        SaleItem secondSaleItem = new SaleItem(sale, albumPop);
        secondSaleItem.calculateCashback();

        sale.getSaleItems().add(firstSaleItem);
        sale.getSaleItems().add(secondSaleItem);

        sale.calculateTotalCashback();

        Assertions.assertThat(sale.getTotalCashback()).isEqualTo(30.40);
    }

    @Test
    public void shouldCalculateTotalCashbackWithTwoItemsWithCashback() {
        Album albumRock = new Album("Artist 1", "Album 1", new Genre("ROCK"), 99.99);
        Album albumPop = new Album("Artist 1", "Album 1", new Genre("POP"), 49.99);
        CashbackDiscount rockCashback = new CashbackDiscount(new Genre("ROCK"), 40.0, DayOfWeek.SUNDAY, true);
        CashbackDiscount popCashback = new CashbackDiscount(new Genre("POP"), 25.0, DayOfWeek.SUNDAY);

        Sale sale = new Sale();

        SaleItem firstSaleItem = new SaleItem(sale, albumRock);
        firstSaleItem.setCashbackDiscount(rockCashback);
        firstSaleItem.calculateCashback();
        SaleItem secondSaleItem = new SaleItem(sale, albumPop);
        secondSaleItem.setCashbackDiscount(popCashback);
        secondSaleItem.calculateCashback();

        sale.getSaleItems().add(firstSaleItem);
        sale.getSaleItems().add(secondSaleItem);

        sale.calculateTotalCashback();

        Assertions.assertThat(sale.getTotalCashback()).isEqualTo(52.5);
    }

    @Test
    public void shouldCalculateTotalPriceAndtotalCashback() {
        Album albumRock = new Album("Artist 1", "Album 1", new Genre("ROCK"), 150.99);
        Album albumPop = new Album("Artist 1", "Album 1", new Genre("POP"), 89.99);
        CashbackDiscount rockCashback = new CashbackDiscount(new Genre("ROCK"), 40.0, DayOfWeek.SUNDAY, true);
        CashbackDiscount popCashback = new CashbackDiscount(new Genre("POP"), 25.0, DayOfWeek.SUNDAY);

        Sale sale = new Sale();

        SaleItem firstSaleItem = new SaleItem(sale, albumRock);
        firstSaleItem.setCashbackDiscount(rockCashback);
        firstSaleItem.calculateCashback();
        SaleItem secondSaleItem = new SaleItem(sale, albumPop);
        secondSaleItem.setCashbackDiscount(popCashback);
        secondSaleItem.calculateCashback();

        sale.getSaleItems().add(firstSaleItem);
        sale.getSaleItems().add(secondSaleItem);

        sale.calculateAggregateFields();

        Assertions.assertThat(sale.getTotalPrice()).isEqualTo(240.98);
        Assertions.assertThat(sale.getTotalCashback()).isEqualTo(82.9);
    }

    @Test
    public void shouldCalcualteStatusPendingOfASale() {
        Sale sale = new Sale();

        Assertions.assertThat(sale.getStatus()).isEqualTo(SaleStatus.PENDING);
    }

    @Test
    public void shouldCalculateStatusConcludedOfASale() {
        Sale sale = new Sale();
        sale.completeSale();

        Assertions.assertThat(sale.getStatus()).isEqualTo(SaleStatus.CONCLUDED);
    }

    @Test
    public void shouldCalculateStatusCanceledOfASale() {
        Sale sale = new Sale();
        sale.cancelSale();

        Assertions.assertThat(sale.getStatus()).isEqualTo(SaleStatus.CANCELED);
    }

    @Test
    public void shouldCancelASale() {
        Sale sale = new Sale();
        sale.cancelSale();

        Assertions.assertThat(sale.getCancelDate()).isNotNull();
    }

    @Test
    public void shouldCompleteASale() {
        Sale sale = new Sale();
        sale.completeSale();

        Assertions.assertThat(sale.getFinishDate()).isNotNull();
    }

    @Test(expected = SaleAlreadyCanceledException.class)
    public void shouldNotCancelASaleAlreadyCanceled() {
        Sale sale = new Sale();
        sale.cancelSale();
        sale.cancelSale();
    }

    @Test(expected = SaleAlreadyCanceledException.class)
    public void shouldNotCompleteASaleAreadyCanceled() {
        Sale sale = new Sale();
        sale.cancelSale();
        sale.completeSale();
    }

    @Test(expected = SaleAlreadyConcludedException.class)
    public void shouldNotCompleteASaleAlreadyCompleted() {
        Sale sale = new Sale();
        sale.completeSale();
        sale.completeSale();
    }

    @Test
    public void shouldSetSaleDateWhenCreate() {
        Sale sale = new Sale();

        Assertions.assertThat(sale.getSaleDate()).isNotNull();
    }
}
