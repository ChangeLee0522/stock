package chenjie.stock.financial.statement.domain;

import chenjie.stock.common.domain.StatementRecord;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatementResponse {
    private int returnCode;
    private String message;
    private List<StatementRecord> result;
}
