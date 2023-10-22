package br.gov.sp.fatec.extractload.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static br.gov.sp.fatec.extractload.utils.Constants.DOT_REGEX;
import static br.gov.sp.fatec.extractload.utils.Constants.ONE;
import static br.gov.sp.fatec.extractload.utils.Constants.ZERO;
import static io.micrometer.core.instrument.util.StringUtils.isEmpty;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtractLoadUtils {

    public static String getTableName(String fullTableName) {
        if (isEmpty(fullTableName)) {
            return "";
        }

        String[] tableName = fullTableName.split(DOT_REGEX);

        if (ONE == tableName.length) {
            return tableName[ZERO];
        }

        return tableName[tableName.length - ONE];
    }

    public static String generateSelectIdsQuery(String tableName, String primaryKey) {
        StringBuilder sbSql = new StringBuilder();

        sbSql.append("SELECT ");
        sbSql.append(primaryKey);
        sbSql.append(" FROM ");
        sbSql.append(tableName);
        sbSql.append(" WHERE ");
        sbSql.append(primaryKey);
        sbSql.append(" IN (:ids) ");
        sbSql.append("ORDER BY ");
        sbSql.append(primaryKey);
        sbSql.append(" ASC");

        return sbSql.toString();
    }

    public static String getPrimaryKey(Set<String> primaryKeys) {
        String id = null;
        if (!primaryKeys.isEmpty()) {
            Optional<String> pk = primaryKeys.stream().findFirst();
            if (pk.isPresent()) {
                id = pk.get();
            }
        }
        return id;
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static String findElapsedTime(Date startTime, Date endTime) {
        Duration duration = Duration.between(dateToLocalDateTime(startTime), dateToLocalDateTime(endTime));

        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long mins = duration.toMinutes();
        duration = duration.minusMinutes(mins);
        long secs = duration.getSeconds();
        duration = duration.minusSeconds(secs);
        long millis = duration.toMillis();

        return String.format("%s:%s:%s:%s", pad(hours, 2), pad(mins, 2), pad(secs, 2), pad(millis, 3));
    }

    public static String pad(long value, long length) {
        return String.format("%0" + length + "d", value);
    }

}
