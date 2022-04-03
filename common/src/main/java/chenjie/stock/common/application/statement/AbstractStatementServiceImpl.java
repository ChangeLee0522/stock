package chenjie.stock.common.application.statement;

import chenjie.stock.common.domain.StatementRecord;
import chenjie.stock.common.infrastructure.dao.statement.StatementDao;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public abstract class AbstractStatementServiceImpl implements StatementService {

    protected abstract StatementDao getDao();

    @Override
    public void save(StatementRecord statementRecord) {
        getDao().save(statementRecord);
    }

    @Override
    public void save(List<StatementRecord> statementRecords) {
        getDao().save(statementRecords);
    }

    @Override
    public String get(int code, String item, String date) {
        return getDao().get(code, item, date);
    }

    @Override
    public List<StatementRecord> get(List<Integer> codes, List<String> items, String fromDate, String toDate) {
        return getDao().get(codes, items, fromDate, toDate);
    }

    @Override
    public List<StatementRecord> getAll() {
        return getDao().getAll();
    }
}
