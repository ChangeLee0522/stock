package chenjie.stock.financial.statement.service;

import chenjie.stock.common.application.statement.StatementService;
import chenjie.stock.common.application.statement.StatementServiceFactory;
import chenjie.stock.common.domain.StatementRecord;
import chenjie.stock.common.domain.StatementType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StatementQueryService {
    @Autowired
    private StatementServiceFactory factory;

    public Float getValue(StatementType type, String code, String item, String date) {
        StatementService service = factory.getStatementService(type.name());
        log.info("Querying value from table {} of {} - {} on {}", type.name(), code, item, date);
        return service.get(code, item, date);
    }

    public List<StatementRecord> getValues(StatementType type, List<String> codes, List<String> items, String fromDate, String toDate) {
        StatementService service = factory.getStatementService(type.name());
        log.info("Querying value from table {} of {} - {} from {} to {}", type.name(), codes, items, fromDate, toDate);
        return service.get(codes, items, fromDate, toDate);
    }

    public List<StatementRecord> getAllValues(StatementType type) {
        StatementService service = factory.getStatementService(type.name());
        log.info("Querying all values from table {}", type.name());
        return service.getAll();
    }

}
