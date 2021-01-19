package com.futao.imooc.learn.dianpin.util;

import java.time.*;

/**
 * 时间工具类
 *
 * @author ft
 * @date 2021/1/19
 */
public class TimeUtils {

    public static LocalDateTime longToLocalDateTime(Long time) {
        if (time == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }


    public static LocalDate longToLocalDate(Long time) {
        if (time == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).toLocalDate();
    }

    public static LocalTime longToLocalTime(Long time) {
        if (time == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(time);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone).toLocalTime();
    }


}
