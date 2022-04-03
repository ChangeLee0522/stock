package chenjie.stock.common.application.statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StatementServiceFactory {
    @Autowired
    private Map<String, StatementService> statementServiceMap;

    public StatementService getStatementService(String statementType) {
        return statementServiceMap.get(statementType);
    }
}
