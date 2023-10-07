package br.gov.sp.fatec.extractload.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static br.gov.sp.fatec.extractload.utils.Constants.*;
import static io.micrometer.core.instrument.util.StringUtils.isEmpty;
import static org.springframework.util.StringUtils.capitalize;

public class ExtractLoadUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ExtractLoadUtils.class);

    private static final String ISO_DATE_FORMAT_ZERO_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private ExtractLoadUtils() {
    }

    private static SimpleDateFormat provideDateFormat() {
        return new SimpleDateFormat(ISO_DATE_FORMAT_ZERO_OFFSET);
    }

//    public static Date toDate(String date, String pattern) throws ParseException {
//        return DateUtils.parseDate(date, pattern);
//    }

    public static Timestamp toTimestamp(String value) {
        try {
            Date date = provideDateFormat().parse(value);
            return new Timestamp(date.getTime());
        } catch (ParseException ex) {
            LOG.error("Timestamp conversion failed!", ex);
            throw new RuntimeException("Timestamp conversion failed!");
        }
    }

    public static Long toLong(String value) {
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException ex) {
            LOG.error("Long conversion failed!", ex);
            throw new NumberFormatException("Parameter jobExecutionId is invalid.");
        }
    }

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);

        return formatter.format(date);
    }

    public static Date toDate(LocalDateTime localDateTime) {
        Date date = null;
        try {
            date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            LOG.error("Date conversion failed!", e);
            return null;
        }

        return date;
    }

    public static Date addInDate(Date date, int hours, int minutes, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.add(Calendar.MINUTE, minutes);
        calendar.add(Calendar.SECOND, seconds);

        return calendar.getTime();
    }

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

    public static String generateDefaultQuery(String tableName, Set<String> primaryKeys) {
        StringBuilder sbSql = new StringBuilder();

        sbSql.append("SELECT * FROM ");
        sbSql.append(tableName);
        sbSql.append(" ORDER BY ");
        sbSql.append(getPrimaryKey(primaryKeys));
        sbSql.append(" ASC");

        return sbSql.toString();
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
}
