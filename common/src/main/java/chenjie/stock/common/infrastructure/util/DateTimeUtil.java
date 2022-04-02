package chenjie.stock.common.infrastructure.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String convertDate(String inputDate, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(inputDate);
        return simpleDateFormat.format(date);
    }
}
