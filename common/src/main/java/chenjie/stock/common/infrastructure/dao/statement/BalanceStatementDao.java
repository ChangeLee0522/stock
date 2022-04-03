package chenjie.stock.common.infrastructure.dao.statement;

import org.springframework.stereotype.Repository;

@Repository
public class BalanceStatementDao extends AbstractStatementDaoImpl {
    private static final String TABLE_NAME = "balance";

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

}
