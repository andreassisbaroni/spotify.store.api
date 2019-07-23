package br.com.baroni.spotify.store.api.application.controller;

import br.com.baroni.spotify.store.api.application.command.CreateSaleCommand;
import br.com.baroni.spotify.store.api.application.query.SaleQuery;
import br.com.baroni.spotify.store.api.domain.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/sales")
public class SaleController implements Serializable {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleQuery> createOrder(@RequestBody @Valid CreateSaleCommand orderCommand) {
        SaleQuery saleQuery = this.saleService.create(orderCommand);

        return new ResponseEntity<>(saleQuery, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleQuery> findById(@PathVariable(value = "id") UUID id) {
        SaleQuery saleQuery = this.saleService.findById(id);
        return new ResponseEntity<>(saleQuery, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<SaleQuery>> findAllBySaleDateBetween(@RequestParam(value = "initialDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime initialDateTime,
                                                                    @RequestParam(value = "finalDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finalDateTime,
                                                                    Pageable pageable) {
        return new ResponseEntity<>(this.saleService.findAllBySaleDateBetween(Optional.ofNullable(initialDateTime),
                Optional.ofNullable(finalDateTime),
                pageable),
                HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/conclusion", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleQuery> finishSale(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(this.saleService.completeSale(id), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/cancellation", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleQuery> cancelSale(@PathVariable(value = "id") UUID id) {
        return new ResponseEntity<>(this.saleService.cancelSale(id), HttpStatus.OK);
    }
}
