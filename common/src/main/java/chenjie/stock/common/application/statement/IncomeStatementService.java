package chenjie.stock.common.application.statement;

import chenjie.stock.common.infrastructure.dao.statement.IncomeStatementDao;
import chenjie.stock.common.infrastructure.dao.statement.StatementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("income")
public class IncomeStatementService extends AbstractStatementServiceImpl {

    @Autowired
    private IncomeStatementDao statementsDao;

    @Override
    protected StatementDao getDao() {
        return statementsDao;
    }
}
