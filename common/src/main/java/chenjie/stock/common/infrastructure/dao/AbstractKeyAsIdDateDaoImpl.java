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
public abstract class AbstractKeyAsIdDateDaoImpl extends AbstractPriceDaoImpl {
    @Override
    public void save(String id, String date, Map<String, String> fieldToValueMap) {
        try {
            String rowKey = id + ROW_KEY_DELIMITER + getReversedDateStr(date);
            Put put = new Put(Bytes.toBytes(rowKey));
            for (String field : fieldToValueMap.keySet()) {
                put.addColumn(getColumnFamilyInBytes(), Bytes.toBytes(field), Bytes.toBytes(fieldToValueMap.get(field)));
            }
            hBaseStore.put(put, getTableName());
        } catch (ParseException e) {
            log.error("Failed to parse date for {}", date, e);
        }
    }

    @Override
    public Map<String, String> get(String id, String date) {
        Map<String, String> ret = new HashMap<>();
        try {
            String rowKey = id + ROW_KEY_DELIMITER + getReversedDateStr(date);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addFamily(getColumnFamilyInBytes());
            Result result = hBaseStore.get(get, getTableName());
            if (result != null && !result.isEmpty()) {
                for (Cell cell : result.listCells()) {
                    String field = Bytes.toString(CellUtil.cloneQualifier(cell));
                    String value = Bytes.toString(CellUtil.cloneValue(cell));
                    ret.put(field, value);
                }
            }
        } catch (ParseException e) {
            log.error("Failed to parse date for {}", date, e);
        }
        return ret;
    }

    @Override
    public String get(String id, String date, String field) {
        try {
            String rowKey = id + ROW_KEY_DELIMITER + getReversedDateStr(date);
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(getColumnFamilyInBytes(), Bytes.toBytes(field));
            Result result = hBaseStore.get(get, getTableName());
            if (result != null && !result.isEmpty()) {
                return Bytes.toString(result.getValue(getColumnFamilyInBytes(), Bytes.toBytes(field)));
            }
        } catch (ParseException e) {
            log.error("Failed to parse date for {}", date, e);
        }
        return null;
    }

}
