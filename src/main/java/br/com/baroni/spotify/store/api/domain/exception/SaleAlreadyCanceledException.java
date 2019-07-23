package br.com.baroni.spotify.store.api.domain.exception;

public class SaleAlreadyCanceledException extends GenericException {

    public SaleAlreadyCanceledException() {
        super("exception.saleAlreadyCanceled");
    }
}
