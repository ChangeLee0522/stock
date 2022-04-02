package chenjie.stock.common.application.statements;

import chenjie.stock.common.infrastructure.dao.statements.IncomeStatementsDao;
import chenjie.stock.common.infrastructure.dao.statements.StatementsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("income")
public class IncomeStatementsService extends AbstractStatementsServiceImpl {

    @Autowired
    private IncomeStatementsDao statementsDao;

    @Override
    protected StatementsDao getDao() {
        return statementsDao;
    }
}
