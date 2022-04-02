package chenjie.stock.common.infrastructure.dao.statements;

import chenjie.stock.common.domain.Record;
import chenjie.stock.common.infrastructure.hbase.HBaseStore;
import chenjie.stock.common.infrastructure.util.DateTimeUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractStatementsDaoImpl implements StatementsDao {

    @Autowired
    private HBaseStore hBaseStore;

    protected abstract String getTableName();

    protected abstract byte[] getColumnFamilyInBytes();

    private static final String ROW_KEY_DELIMITER = "_";
    private static final String DATE_LIMIT = "99999999";
    private static final String DATE_PATTERN = "yyyyMMdd";

    @Override
    public void save(String code, List<Record> records) {
        List<Put> puts = new ArrayList<>();
        for (Record record : records) {
            Put put = getPut(code, record);
            puts.add(put);
        }
        hBaseStore.put(puts, getTableName());
    }

    @Override
    public List<Record> get(String code, List<String> items, List<String> dates) {
        List<Get> gets = new ArrayList<>();
        for (String item : items) {
            String rowKey = code + ROW_KEY_DELIMITER + item;
            Get get = new Get(Bytes.toBytes(rowKey));
            for (String date : dates) {
                get.addColumn(getColumnFamilyInBytes(), getColumnInBytes(date));
            }
            gets.add(get);
        }
        Result[] results = hBaseStore.get(gets, getTableName());
        return getRecordsFromResults(results);
    }

    private List<Record> getRecordsFromResults(Result[] results) {
        List<Record> ret = new ArrayList<>();
        if (results != null && results.length > 0) {
            for (Result result : results) {
                Record record = new Record();
                String rowKey = Bytes.toString(result.getRow());
                record.setItem(rowKey.substring(rowKey.indexOf(ROW_KEY_DELIMITER + 1)));
                Map<String, String> map = new HashMap<>();
                record.setDateToValueMaps(map);
                for (Cell cell : result.listCells()) {
                    String date = getColumnInString(CellUtil.cloneQualifier(cell));
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    map.put(date, value);
                }
                ret.add(record);
            }
        }
        return ret;
    }

    private Put getPut(String code, Record record) {
        String rowKey = code + ROW_KEY_DELIMITER + record.getItem();
        Put put = new Put(Bytes.toBytes(rowKey));
        for (String date : record.getDateToValueMaps().keySet()) {
            if (getColumnInBytes(date) != null) {
                put.addColumn(getColumnFamilyInBytes(), getColumnInBytes(date), Bytes.toBytes(record.getDateToValueMaps().get(date)));
            }
        }
        return put;
    }

    private byte[] getColumnInBytes(String date) {
        try {
            String outputDate = DateTimeUtil.convertDate(date, DATE_PATTERN);
            String col = String.valueOf(Integer.parseInt(DATE_LIMIT) - Integer.parseInt(outputDate));
            return Bytes.toBytes(col);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getColumnInString(byte[] bytes) {
        String col = Bytes.toString(bytes);
        return String.valueOf(Integer.parseInt(DATE_LIMIT) - Integer.parseInt(col));
    }
}
