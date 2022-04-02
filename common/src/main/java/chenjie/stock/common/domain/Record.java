package chenjie.stock.common.domain;

import lombok.Data;

import java.util.Map;

@Data
public class Record {
    private String item;
    private Map<String, String> dateToValueMaps;
}
