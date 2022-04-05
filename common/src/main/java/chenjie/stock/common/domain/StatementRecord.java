package chenjie.stock.common.domain;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class StatementRecord {
    private String code;
    private String item;
    private Date date;
    private String value;
}
