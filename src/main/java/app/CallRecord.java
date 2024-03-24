package app;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Запись о звонке.
 * Хранит информацию о звонке -- идентификатор абонента, тип звонка, время начала и окончания.
 */
public record CallRecord(String msisdn, String callType, long startTime, long endTime) {

    /**
     * Получает длительность звонка.
     * @return Длительность звонка в секундах.
     */
    public long getDuration() {
        return endTime - startTime;
    }

    /**
     * Получает месяц звонка на основе времени начала.
     * @return Строковое представление месяца звонка(в виде числового значения).
     */
    public String getMonth() {
        Instant instant = Instant.ofEpochSecond(startTime);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        return DateTimeFormatter.ofPattern("MM").format(zdt);
    }
}

