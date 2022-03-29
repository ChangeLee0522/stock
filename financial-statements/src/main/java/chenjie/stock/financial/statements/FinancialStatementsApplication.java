package chenjie.stock.financial.statements;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class FinancialStatementsApplication {

	public static void main(String[] args) {
		log.info("Start financial statements main class");
		SpringApplication.run(FinancialStatementsApplication.class, args);
	}
}
