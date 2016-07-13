package es.amplia.commons.mongodb.converters;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Date;

@ReadingConverter
public class DateToLocalDateTimeConverter implements Converter<Date, LocalDateTime> {

    public LocalDateTime convert(Date source) {
        if (source == null)
            return null;

        LocalDateTime dt = LocalDateTime.fromDateFields(source);

        int offset = DateTimeZone.getDefault().getOffsetFromLocal(source.getTime());
        return dt.minusMillis(offset);
    }

}
