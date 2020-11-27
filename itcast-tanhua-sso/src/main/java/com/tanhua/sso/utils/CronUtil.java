package com.tanhua.sso.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CronUtil {
    public static String formatDateByPattern(Date date, String dateFormat) {
        dateFormat = dateFormat == null ? "yyyy-MM-dd HH:mm:ss" : dateFormat;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return date != null ? sdf.format(date) : null;
    }

    /**
     * 生成cron表达式
     * format? null  sdf.format() date
     * 生成cron表达式 ss mm HH dd MM ? yyyy
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     *
     * @param date : 时间点
     */
    public static String getCron(Date date) {
        String dateFormat = "ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }


}
