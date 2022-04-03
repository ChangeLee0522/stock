package chenjie.stock.common.infrastructure.dao;

import java.util.Map;

public interface PriceDataDao {
    void save(String id, String key, Map<String, String> columnToValueMap);

    Map<String, String> get(String id, String key);

    String get(String id, String key, String col);
}
