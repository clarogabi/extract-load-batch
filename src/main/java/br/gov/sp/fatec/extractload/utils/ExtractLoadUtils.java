package br.gov.sp.fatec.extractload.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Locale;

import static br.gov.sp.fatec.extractload.utils.Constants.DOT_REGEX;
import static br.gov.sp.fatec.extractload.utils.Constants.ONE;
import static br.gov.sp.fatec.extractload.utils.Constants.ZERO;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtractLoadUtils {

    public static String getTableName(final String fullTableName) {
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

    public static String getPrimaryKey(Collection<String> primaryKeys) {
        if (!primaryKeys.isEmpty()) {
            return primaryKeys.stream().findFirst()
                .orElse(null);
        }
        return null;
    }

    public static String findElapsedTime(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds();
        duration = duration.minusSeconds(seconds);
        long millis = duration.toMillis();

        return String.format("%s:%s:%s:%s", pad(hours, 2), pad(minutes, 2), pad(seconds, 2), pad(millis, 3));
    }

    public static String pad(long value, long length) {
        return String.format("%0" + length + "d", value);
    }

    public static String textNormalizer(String text) {
        return text.trim().toUpperCase(Locale.getDefault());
    }

}
