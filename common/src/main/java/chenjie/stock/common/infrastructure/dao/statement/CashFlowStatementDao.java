package chenjie.stock.common.infrastructure.dao.statement;

import org.springframework.stereotype.Repository;

@Repository
public class CashFlowStatementDao extends AbstractStatementDaoImpl {
    private static final String TABLE_NAME = "cash_flow";

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
