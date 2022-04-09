package chenjie.stock.financial.statement.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StatementRequest {
    private List<String> items;
    private List<String> codes;
    private String fromDate;
    private String toDate;
}
