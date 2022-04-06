package chenjie.stock.financial.statement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
@ComponentScan(basePackages = "chenjie.stock")
public class FinancialStatementApplication {

	public static void main(String[] args) {
		log.info("Start financial statements main class");
		SpringApplication.run(FinancialStatementApplication.class, args);
	}
}
