package chenjie.stock.common.infrastructure.dao;

import org.apache.hadoop.hbase.util.Bytes;

public class CashFlowStatementsDao extends AbstractStatementsDaoImpl {
    private static final String TABLE_NAME = "cash_flow_statements";
    private static final String CF = "c";
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
