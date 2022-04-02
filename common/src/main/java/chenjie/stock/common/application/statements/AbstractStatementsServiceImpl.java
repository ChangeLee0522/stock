package chenjie.stock.common.application.statements;

import chenjie.stock.common.domain.Record;
import chenjie.stock.common.infrastructure.dao.statements.StatementsDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class AbstractStatementsServiceImpl implements StatementsService {

    protected abstract StatementsDao getDao();

    @Override
    public void save(String code, List<Record> records) {
        getDao().save(code, records);
    }

    @Override
    public List<Record> get(String code, List<String> items, List<String> dates) {
        return getDao().get(code, items, dates);
    }
}
