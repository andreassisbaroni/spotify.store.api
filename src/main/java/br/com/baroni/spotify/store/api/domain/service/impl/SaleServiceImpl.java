package br.com.baroni.spotify.store.api.domain.service.impl;

import br.com.baroni.spotify.store.api.application.command.AddSaleItemCommand;
import br.com.baroni.spotify.store.api.application.command.CreateSaleCommand;
import br.com.baroni.spotify.store.api.application.query.SaleQuery;
import br.com.baroni.spotify.store.api.domain.entity.Sale;
import br.com.baroni.spotify.store.api.domain.exception.EntityNotFoundException;
import br.com.baroni.spotify.store.api.domain.service.SaleItemService;
import br.com.baroni.spotify.store.api.domain.service.SaleService;
import br.com.baroni.spotify.store.api.infra.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemService saleItemService;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, SaleItemService saleItemService) {
        this.saleRepository = saleRepository;
        this.saleItemService = saleItemService;
    }

    @Override
    @Transactional
    public SaleQuery create(CreateSaleCommand orderCommand) {
        Sale sale = new Sale();

        this.saleRepository.save(sale);

        sale.setSaleItems(orderCommand.getSaleItems().stream().map(
                orderItemCommand -> {
                    AddSaleItemCommand newOrderItem = new AddSaleItemCommand(sale.getId(), orderItemCommand.getId());
                    return this.saleItemService.addNewOrderItemToOrder(newOrderItem);
                }
        ).collect(Collectors.toList()));

        sale.calculateAggregateFields();

        return this.createSaleQueryFromSale(sale);
    }

    @Override
    public SaleQuery findById(UUID id) {
        return this.createSaleQueryFromSale(this.saleRepository.findById(id).orElseThrow(EntityExistsException::new));
    }

    public Page<SaleQuery> findAllBySaleDateBetween(Optional<LocalDateTime> initialDateTime, Optional<LocalDateTime> finalDateTime, Pageable pageable) {
        Page<Sale> salePage;
        if (initialDateTime.isPresent() && finalDateTime.isPresent()) {
            salePage = this.saleRepository.findAllBySaleDateBetweenOrderBySaleDateDesc(initialDateTime.get(), finalDateTime.get(), pageable);
        } else if (initialDateTime.isPresent()) {
            salePage = this.saleRepository.findBySaleDateGreaterThanEqualOrderBySaleDateDesc(initialDateTime.get(), pageable);
        } else if (finalDateTime.isPresent()) {
            salePage = this.saleRepository.findBySaleDateLessThanEqualOrderBySaleDateDesc(finalDateTime.get(), pageable);
        } else {
            salePage = this.saleRepository.findAll(pageable);
        }

        return new PageImpl<>(salePage.getContent().parallelStream().map(
                this::createSaleQueryFromSale
        ).collect(Collectors.toList()));
    }

    @Transactional
    public SaleQuery completeSale(UUID id) {
        Sale sale = this.saleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Sale.class));
        sale.completeSale();
        this.saleRepository.save(sale);

        return this.createSaleQueryFromSale(sale);
    }

    @Transactional
    public SaleQuery cancelSale(UUID id) {
        Sale sale = this.saleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Sale.class));
        sale.cancelSale();
        this.saleRepository.save(sale);

        return this.createSaleQueryFromSale(sale);
    }

    private SaleQuery createSaleQueryFromSale(Sale sale) {
        SaleQuery saleQuery = new SaleQuery(sale.getId(), sale.getSaleDate(), sale.getStatus());
        saleQuery.setTotalPrice(sale.getTotalPrice());
        saleQuery.setTotalCashback(sale.getTotalCashback());
        return saleQuery;
    }

}
