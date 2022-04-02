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
}
