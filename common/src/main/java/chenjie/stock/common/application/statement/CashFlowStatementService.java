package chenjie.stock.common.application.statement;

import chenjie.stock.common.infrastructure.dao.statement.CashFlowStatementDao;
import chenjie.stock.common.infrastructure.dao.statement.StatementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cash_flow")
public class CashFlowStatementService extends AbstractStatementServiceImpl {
    @Autowired
    private CashFlowStatementDao cashFlowStatementsDao;

    @Override
    protected StatementDao getDao() {
        return cashFlowStatementsDao;
    }
}
