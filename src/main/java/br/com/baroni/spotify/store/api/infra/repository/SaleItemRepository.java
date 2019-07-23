package br.com.baroni.spotify.store.api.infra.repository;

import br.com.baroni.spotify.store.api.domain.entity.SaleItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, UUID> {

    Page<SaleItem> findBySale_IdOrderById(UUID saleId, Pageable pageable);
}
