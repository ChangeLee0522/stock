package chenjie.stock.financial.statement.service;

import chenjie.stock.common.application.statement.BalanceStatementService;
import chenjie.stock.common.application.statement.StatementServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class StatementLoaderServiceTest {

    @Mock
    private StatementServiceFactory factory;

    @Mock
    private BalanceStatementService statementService;

    @InjectMocks
    private StatementLoaderService loaderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(factory.getStatementService(Mockito.anyString())).thenReturn(statementService);
        Mockito.doNothing().when(statementService).save(Mockito.anyList());
    }

    @Test
    void loadDataFromCsvFile() throws IOException, ParseException {
        String filePath = "/Users/lichenjie/Documents/finance/sheet/balance/zcfzb000001.csv";
        loaderService.loadDataFromCsvFile(filePath, null);
    }
}