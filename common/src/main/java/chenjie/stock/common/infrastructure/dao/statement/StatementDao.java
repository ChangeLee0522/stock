package chenjie.stock.common.infrastructure.dao.statement;

import chenjie.stock.common.domain.StatementRecord;

import java.util.List;

public interface StatementDao {
    void save(StatementRecord statementRecord);

    void save(List<StatementRecord> statementRecords);

    String get(String code, String item, String date);

    List<StatementRecord> get(List<String> codes, List<String> items, String fromDate, String toDate);

    List<StatementRecord> getAll();
}
