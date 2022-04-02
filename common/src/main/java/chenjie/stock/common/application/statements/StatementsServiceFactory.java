package chenjie.stock.common.application.statements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StatementsServiceFactory {
    @Autowired
    private Map<String, StatementsService> statementsServiceMap;

    public StatementsService getStatementsService(String statementsType) {
        return statementsServiceMap.get(statementsType);
    }
}
