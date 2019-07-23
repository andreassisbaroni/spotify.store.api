package br.com.baroni.spotify.store.api.infra.repository;

import br.com.baroni.spotify.store.api.domain.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {

    Page<Sale> findAllBySaleDateBetweenOrderBySaleDateDesc(LocalDateTime inicialDateTime, LocalDateTime finalDateTime, Pageable pageable);

    Page<Sale> findBySaleDateGreaterThanEqualOrderBySaleDateDesc(LocalDateTime inicialDateTime, Pageable pageable);

    Page<Sale> findBySaleDateLessThanEqualOrderBySaleDateDesc(LocalDateTime finalDateTime, Pageable pageable);
}
