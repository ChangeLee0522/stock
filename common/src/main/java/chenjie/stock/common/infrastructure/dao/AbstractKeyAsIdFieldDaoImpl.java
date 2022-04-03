package chenjie.stock.common.infrastructure.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public abstract class AbstractKeyAsIdFieldDaoImpl extends AbstractPriceDaoImpl {
    @Override
    public void save(String id, String field, Map<String, String> dateToValueMap) {
        String rowKey = id + ROW_KEY_DELIMITER + field;
        Put put = new Put(Bytes.toBytes(rowKey));
        for (String date : dateToValueMap.keySet()) {
            try {
                put.addColumn(getColumnFamilyInBytes(), Bytes.toBytes(getReversedDateStr(date)), Bytes.toBytes(dateToValueMap.get(date)));
            } catch (ParseException e) {
                log.error("Failed to parse date for {}, ignore.", date, e);
            }
        }
        hBaseStore.put(put, getTableName());
    }

    @Override
    public Map<String, String> get(String id, String field) {
        Map<String, String> ret = new HashMap<>();
        String rowKey = id + ROW_KEY_DELIMITER + field;
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addFamily(getColumnFamilyInBytes());
        Result result = hBaseStore.get(get, getTableName());
        if (result != null && !result.isEmpty()) {
            for (Cell cell : result.listCells()) {
                try {
                    String date = getReversedDateStr(Bytes.toString(CellUtil.cloneQualifier(cell)));
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    ret.put(date, value);
                } catch (ParseException e) {
                    log.error("Failed to parse date from db, ignore", e);
                }
            }
        }
        return ret;
    }

    @Override
    public String get(String id, String field, String date) {
        String rowKey = id + ROW_KEY_DELIMITER + field;
        Get get = new Get(Bytes.toBytes(rowKey));
        try {
            get.addColumn(getColumnFamilyInBytes(), Bytes.toBytes(getReversedDateStr(date)));
            Result result = hBaseStore.get(get, getTableName());
            if (result != null && !result.isEmpty()) {
                return Bytes.toString(result.getValue(getColumnFamilyInBytes(), Bytes.toBytes(getReversedDateStr(date))));
            }
        } catch (ParseException e) {
            log.error("Failed to parse date for {}, ignore.", date, e);
        }
        return null;
    }
}
