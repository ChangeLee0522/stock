package chenjie.stock.common.infrastructure.dao.statements;

import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Component;

@Component
public class IncomeStatementsDao extends AbstractStatementsDaoImpl {
    private static final String TABLE_NAME = "income_statements";
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
