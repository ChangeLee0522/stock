package chenjie.stock.common.application.statements;

import chenjie.stock.common.infrastructure.dao.statements.CashFlowStatementsDao;
import chenjie.stock.common.infrastructure.dao.statements.StatementsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cashFlow")
public class CashFlowStatementsService extends AbstractStatementsServiceImpl {
    @Autowired
    private CashFlowStatementsDao cashFlowStatementsDao;

    @Override
    protected StatementsDao getDao() {
        return cashFlowStatementsDao;
    }
}
