package chenjie.stock.common.infrastructure.dao.statement;

import chenjie.stock.common.domain.StatementRecord;

import java.util.List;

public interface StatementDao {
    void save(StatementRecord statementRecord);

    void save(List<StatementRecord> statementRecords);

    String get(int code, String item, String date);

    List<StatementRecord> get(List<Integer> codes, List<String> items, String fromDate, String toDate);

    List<StatementRecord> getAll();
}
