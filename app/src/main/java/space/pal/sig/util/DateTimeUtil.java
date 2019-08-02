package space.pal.sig.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DISPLAY_DATE_FORMAT = "dd-MMM-yyyy";

    public static String formatTimestamp(long timestamp) {
        return dateToString(new Date(timestamp * 1000), DISPLAY_DATE_FORMAT);
    }

    public static String dateToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String dateToString(Calendar calendar, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    public static Date stringToDate(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String formatNumber(long number) {
        String numberStr;
        if (number < 10) {
            numberStr = String.format("0%s", number);
        } else {
            numberStr = String.valueOf(number);
        }
        return numberStr;
    }
}
