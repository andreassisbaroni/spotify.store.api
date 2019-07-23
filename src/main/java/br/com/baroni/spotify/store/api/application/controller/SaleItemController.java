package br.com.baroni.spotify.store.api.application.controller;

import br.com.baroni.spotify.store.api.application.query.SaleItemQuery;
import br.com.baroni.spotify.store.api.domain.service.SaleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales/{saleId}/items")
public class SaleItemController implements Serializable {

    private final SaleItemService saleItemService;

    @Autowired
    public SaleItemController(SaleItemService saleItemService) {
        this.saleItemService = saleItemService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SaleItemQuery>> findAllBySaleId(@PathVariable(value = "saleId") UUID saleId,
                                                               Pageable pageable) {
        Page<SaleItemQuery> saleItemQueries = this.saleItemService.findBySaleId(saleId, pageable);
        return new ResponseEntity<>(saleItemQueries, HttpStatus.OK);
    }
}
