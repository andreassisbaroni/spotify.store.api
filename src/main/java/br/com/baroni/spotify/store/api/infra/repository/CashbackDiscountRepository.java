package br.com.baroni.spotify.store.api.infra.repository;

import br.com.baroni.spotify.store.api.domain.entity.CashbackDiscount;
import br.com.baroni.spotify.store.api.domain.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CashbackDiscountRepository extends JpaRepository<CashbackDiscount, UUID> {

    Optional<CashbackDiscount> findByGenreAndDayOfWeekAndActive(Genre genre, DayOfWeek dayOfWeek, Boolean active);
}
