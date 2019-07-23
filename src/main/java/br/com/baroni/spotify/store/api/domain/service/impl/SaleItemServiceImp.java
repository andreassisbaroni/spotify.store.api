package br.com.baroni.spotify.store.api.domain.service.impl;

import br.com.baroni.spotify.store.api.application.command.AddSaleItemCommand;
import br.com.baroni.spotify.store.api.application.query.SaleItemQuery;
import br.com.baroni.spotify.store.api.domain.entity.Album;
import br.com.baroni.spotify.store.api.domain.entity.Sale;
import br.com.baroni.spotify.store.api.domain.entity.SaleItem;
import br.com.baroni.spotify.store.api.domain.exception.EntityNotFoundException;
import br.com.baroni.spotify.store.api.domain.service.SaleItemService;
import br.com.baroni.spotify.store.api.infra.repository.AlbumRepository;
import br.com.baroni.spotify.store.api.infra.repository.CashbackDiscountRepository;
import br.com.baroni.spotify.store.api.infra.repository.SaleItemRepository;
import br.com.baroni.spotify.store.api.infra.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleItemServiceImp implements SaleItemService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final AlbumRepository albumRepository;
    private final CashbackDiscountRepository cashbackDiscountRepository;

    @Autowired
    public SaleItemServiceImp(SaleRepository saleRepository, SaleItemRepository saleItemRepository,
                              AlbumRepository albumRepository, CashbackDiscountRepository cashbackDiscountRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.albumRepository = albumRepository;
        this.cashbackDiscountRepository = cashbackDiscountRepository;
    }

    @Override
    @Transactional
    public SaleItem addNewOrderItemToOrder(AddSaleItemCommand addSaleItemCommand) {
        SaleItem saleItem = new SaleItem(this.saleRepository.findById(addSaleItemCommand.getSaleId()).orElseThrow(() -> new EntityNotFoundException(Sale.class)),
                this.albumRepository.findById(addSaleItemCommand.getAlbumId()).orElseThrow(() -> new EntityNotFoundException(Album.class)));

        saleItem.setCashbackDiscount(this.cashbackDiscountRepository.findByGenreAndDayOfWeekAndActive(saleItem.getAlbum().getGenre(),
                LocalDateTime.now().getDayOfWeek(),
                Boolean.TRUE).orElse(null));

        saleItem.calculateCashback();

        return this.saleItemRepository.save(saleItem);
    }

    @Override
    public Page<SaleItemQuery> findBySaleId(UUID saleId, Pageable pageable) {
        Page<SaleItem> saleItemPage = this.saleItemRepository.findBySale_IdOrderById(saleId, pageable);

        return new PageImpl<>(saleItemPage.getContent().parallelStream().map(
                saleItem -> new SaleItemQuery(saleId, saleItem.getAlbum().getName(), saleItem.getPrice(), saleItem.getCashback())
        ).collect(Collectors.toList()));
    }
}
