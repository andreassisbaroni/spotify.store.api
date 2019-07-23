package br.com.baroni.spotify.store.api.infra.internationalization;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class CustomLocaleResolver extends SessionLocaleResolver {

    private static final Collection<Locale> LOCALES = Arrays.asList(new Locale("en"),
            new Locale("pt_BR"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (StringUtils.isEmpty(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));

        return Locale.lookup(list, LOCALES);
    }
}
