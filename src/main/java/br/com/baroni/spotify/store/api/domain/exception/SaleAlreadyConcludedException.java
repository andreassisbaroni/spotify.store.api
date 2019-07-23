package br.com.baroni.spotify.store.api.domain.exception;

public class SaleAlreadyConcludedException extends GenericException {

    public SaleAlreadyConcludedException() {
        super("exception.saleAlreadyConcluded");
    }
}
