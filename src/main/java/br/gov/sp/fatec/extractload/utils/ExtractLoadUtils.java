package br.gov.sp.fatec.extractload.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Pattern;

import static br.gov.sp.fatec.extractload.utils.Constants.DOT_REGEX;
import static br.gov.sp.fatec.extractload.utils.Constants.ONE;
import static br.gov.sp.fatec.extractload.utils.Constants.THREE;
import static br.gov.sp.fatec.extractload.utils.Constants.TWO;
import static br.gov.sp.fatec.extractload.utils.Constants.ZERO;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtractLoadUtils {

    private static final Pattern dotRegex = Pattern.compile(DOT_REGEX);

    public static String getTableName(final String fullTableName) {
        if (isEmpty(fullTableName)) {
            return "";
        }

        String[] tableName = fullTableName.split(dotRegex.pattern());

        if (ONE == tableName.length) {
            return tableName[ZERO];
        }

        return tableName[tableName.length - ONE];
    }

    public static String generateSelectIdsQuery(final String tableName, final String primaryKey) {
        return String.format("SELECT %s FROM %s WHERE %s IN (:ids) ORDER BY %s ASC", primaryKey, tableName, primaryKey, primaryKey);
    }

    public static String getPrimaryKey(final Collection<String> primaryKeys) {
        if (!primaryKeys.isEmpty()) {
            return primaryKeys.stream().findFirst()
                .orElse(null);
        }
        return null;
    }

    public static String findElapsedTime(final LocalDateTime startTime, final LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);

        long hours = duration.toHours();
        duration = duration.minusHours(hours);
        long minutes = duration.toMinutes();
        duration = duration.minusMinutes(minutes);
        long seconds = duration.getSeconds();
        duration = duration.minusSeconds(seconds);
        long millis = duration.toMillis();

        return String.format("%s:%s:%s:%s", pad(hours, TWO), pad(minutes, TWO), pad(seconds, TWO), pad(millis, THREE));
    }

    public static String pad(long value, long length) {
        final var padFormat = "%0" + length + "d";
        return String.format(padFormat, value);
    }

    public static String textNormalizer(String text) {
        return text.trim().toUpperCase(Locale.getDefault());
    }

    public static String toUpperCase(String text) {
        return text.toUpperCase(Locale.getDefault());
    }

}
