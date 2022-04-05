package chenjie.stock.common.infrastructure.util;

import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static final String[] FORMATS = new String[]{
            "yyyy-MM-dd", "yyyyMMdd"
    };

    public static String convertDate(String inputDate, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = DateUtils.parseDate(inputDate, FORMATS);
        return simpleDateFormat.format(date);
    }

    public static int compareDates(String benchmark, String toCompare) throws ParseException {
        Date date1 = DateUtils.parseDate(benchmark, FORMATS);
        Date date2 = DateUtils.parseDate(toCompare, FORMATS);
        if (date1.before(date2)) {
            return -1;
        } else if (date1.after(date2)) {
            return 1;
        } else {
            return 0;
        }
    }
}
