package chenjie.stock.common;

import chenjie.stock.common.application.statement.StatementService;
import chenjie.stock.common.application.statement.StatementServiceFactory;
import chenjie.stock.common.domain.StatementRecord;
import chenjie.stock.common.domain.StatementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

//@SpringBootApplication
//public class FakeMainApplication implements ApplicationRunner {
//
//    @Autowired
//    private StatementServiceFactory factory;
//
//    public static void main(String[] args) {
//        SpringApplication.run(FakeMainApplication.class, args);
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        StatementService service = factory.getStatementService(StatementType.balance.name());
//        List<StatementRecord> value = service.getAll();
//        System.out.println(value.size());
//    }
//}
