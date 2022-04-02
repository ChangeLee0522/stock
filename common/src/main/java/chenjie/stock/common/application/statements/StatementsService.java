package chenjie.stock.common.application.statements;

import chenjie.stock.common.domain.Record;

import java.util.List;

public interface StatementsService {
    void save(String code, List<Record> records);

    List<Record> get(String code, List<String> items, List<String> dates);
}
