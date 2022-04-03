package chenjie.stock.common.application.statement;

import chenjie.stock.common.infrastructure.dao.statement.BalanceStatementDao;
import chenjie.stock.common.infrastructure.dao.statement.StatementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("balance")
public class BalanceStatementService extends AbstractStatementServiceImpl {
    @Autowired
    private BalanceStatementDao balanceStatementsDao;

    @Override
    protected StatementDao getDao() {
        return balanceStatementsDao;
    }
}
