package chenjie.stock.common.infrastructure.dao;

import chenjie.stock.common.domain.Record;

import java.util.List;

public interface StatementsDao {
    void save(String code, List<Record> records);

    List<Record> get(String code, List<String> items, List<String> dates);
}
