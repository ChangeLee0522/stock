package chenjie.stock.financial.statements.service;

import chenjie.stock.common.application.statements.StatementsServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatementsLoaderService {

    @Autowired
    private StatementsServiceFactory factory;


}
