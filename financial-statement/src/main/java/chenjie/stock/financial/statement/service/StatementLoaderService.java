package chenjie.stock.financial.statement.service;

import chenjie.stock.common.application.statement.StatementServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementLoaderService {

    @Autowired
    private StatementServiceFactory factory;

    public void loadDataFromCsvFile(String filePath) {

    }
}
