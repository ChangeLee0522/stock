package chenjie.stock.common.infrastructure.dao;

import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

@Repository
public class IntradayPriceDao extends AbstractKeyAsIdFieldDaoImpl {
    private static final String TABLE_NAME = "intraday_price";
    private static final String CF = "i";
    private static final byte[] CF_BYTES = Bytes.toBytes(CF);

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected byte[] getColumnFamilyInBytes() {
        return CF_BYTES;
    }
}
