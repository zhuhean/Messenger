package com.zhuhean.messenger.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateHelper {

    private DateHelper() {
    }

    private static class Holder {
        static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("MM-dd  HH:mm", Locale.CHINA);
        static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.CHINA);
    }

    public static String asTime(long timestamp) {
        Date date = new Date(timestamp);
        long current = System.currentTimeMillis();
        long difference = current - timestamp;
        long differenceInDays = difference / 1000 / 60 / 60 / 24;
        if (differenceInDays > 30) return Holder.DATE_FORMAT.format(date);
        if (differenceInDays > 0) return Holder.DAY_FORMAT.format(date);
        return Holder.TIME_FORMAT.format(date);
    }

}
