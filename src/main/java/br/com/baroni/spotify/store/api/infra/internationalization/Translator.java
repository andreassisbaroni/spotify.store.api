package br.com.baroni.spotify.store.api.infra.internationalization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Locale;

@Component
public class Translator implements Serializable {

    private static MessageSource messageSource;

    @Autowired
    public Translator(MessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String messageCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, null, locale);
    }

    public static String toLocale(String messageCode, Object[] objects) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageCode, objects, locale);
    }
}
