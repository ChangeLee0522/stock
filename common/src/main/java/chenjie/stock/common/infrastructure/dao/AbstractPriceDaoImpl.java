package chenjie.stock.common.infrastructure.dao;

import chenjie.stock.common.infrastructure.hbase.HBaseStore;
import chenjie.stock.common.infrastructure.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

public abstract class AbstractPriceDaoImpl implements PriceDataDao {
    @Autowired
    HBaseStore hBaseStore;

    protected abstract String getTableName();

    protected abstract byte[] getColumnFamilyInBytes();

    static final String ROW_KEY_DELIMITER = "_";
    private static final String DATE_LIMIT = "99999999";
    private static final String DATE_PATTERN = "yyyyMMdd";

    String getReversedDateStr(String date) throws ParseException {
        String outputDate = DateTimeUtil.convertDate(date, DATE_PATTERN);
        return String.valueOf(Integer.parseInt(DATE_LIMIT) - Integer.parseInt(outputDate));
    }
}
