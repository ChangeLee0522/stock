package chenjie.stock.common.infrastructure.dao.statement;

import org.springframework.stereotype.Repository;

@Repository
public class IncomeStatementDao extends AbstractStatementDaoImpl {
    private static final String TABLE_NAME = "income";

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
}
