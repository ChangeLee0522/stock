package chenjie.stock.common.application.statements;

import chenjie.stock.common.infrastructure.dao.statements.BalanceStatementsDao;
import chenjie.stock.common.infrastructure.dao.statements.StatementsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("balance")
public class BalanceStatementsService extends AbstractStatementsServiceImpl {
    @Autowired
    private BalanceStatementsDao balanceStatementsDao;

    @Override
    protected StatementsDao getDao() {
        return balanceStatementsDao;
    }
}
