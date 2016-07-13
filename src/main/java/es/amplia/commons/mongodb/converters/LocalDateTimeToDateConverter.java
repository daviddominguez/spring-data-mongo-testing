package es.amplia.commons.mongodb.converters;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

import java.util.Date;

@WritingConverter
public class LocalDateTimeToDateConverter implements Converter<LocalDateTime, Date> {

    public Date convert(LocalDateTime source) {
        if (source == null)
            return null;

        int offset = DateTimeZone.getDefault().getOffsetFromLocal(source.toDateTime().getMillis());
        return source.plusMillis(offset).toDate();
    }
}
