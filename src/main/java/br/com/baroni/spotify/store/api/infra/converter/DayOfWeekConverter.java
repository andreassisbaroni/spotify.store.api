package br.com.baroni.spotify.store.api.infra.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.DayOfWeek;
import java.util.Objects;

@Converter(autoApply = true)
public class DayOfWeekConverter implements AttributeConverter<DayOfWeek, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DayOfWeek dayOfWeek) {
        if (Objects.nonNull(dayOfWeek)) {
            return dayOfWeek.getValue();
        }

        return null;
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Integer id) {
        if (Objects.nonNull(id)) {
            return DayOfWeek.of(id);
        }

        return null;
    }
}
