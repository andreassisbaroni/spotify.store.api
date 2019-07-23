package br.com.baroni.spotify.store.api.domain.exception;

import br.com.baroni.spotify.store.api.infra.internationalization.Translator;

public class GenericException extends RuntimeException {

    public GenericException(String messageCode) {
        super(Translator.toLocale(messageCode));
    }

    public GenericException(String messageCode, Object[] objects) {
        super(Translator.toLocale(messageCode, objects));
    }
}
