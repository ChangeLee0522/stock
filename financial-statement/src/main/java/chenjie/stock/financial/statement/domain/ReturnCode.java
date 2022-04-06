package chenjie.stock.financial.statement.domain;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class ReturnCode {
    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int INTERNAL_ERROR = 500;

    private static Map<Integer, String> messageMap = new HashMap<>();

    @PostConstruct
    private void init() {
        messageMap.put(SUCCESS, "Success.");
        messageMap.put(BAD_REQUEST, "Bad request, please check.");
        messageMap.put(INTERNAL_ERROR, "Internal server error.");
    }

    public static String getMessage(int code) {
        return messageMap.get(code);
    }
}
