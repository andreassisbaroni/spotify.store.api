package br.com.baroni.spotify.store.api.domain.service;

import br.com.baroni.spotify.store.api.application.command.CreateSaleCommand;
import br.com.baroni.spotify.store.api.application.query.SaleQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SaleService {

    SaleQuery create(CreateSaleCommand orderCommand);

    SaleQuery findById(UUID id);

    Page<SaleQuery> findAllBySaleDateBetween(Optional<LocalDateTime> initialDateTime, Optional<LocalDateTime> finalDateTime, Pageable pageable);

    SaleQuery completeSale(UUID id);

    SaleQuery cancelSale(UUID id);
}
