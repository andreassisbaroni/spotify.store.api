package br.com.baroni.spotify.store.api.domain.service;

import br.com.baroni.spotify.store.api.application.command.AddSaleItemCommand;
import br.com.baroni.spotify.store.api.application.query.SaleItemQuery;
import br.com.baroni.spotify.store.api.domain.entity.SaleItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SaleItemService {

    SaleItem addNewOrderItemToOrder(AddSaleItemCommand addSaleItemCommand);

    Page<SaleItemQuery> findBySaleId(UUID saleId, Pageable pageable);
}
