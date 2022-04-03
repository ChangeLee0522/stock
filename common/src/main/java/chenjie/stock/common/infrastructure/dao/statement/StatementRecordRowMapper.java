package chenjie.stock.common.infrastructure.dao.statement;

import chenjie.stock.common.domain.StatementRecord;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatementRecordRowMapper implements RowMapper<StatementRecord> {
    @Override
    public StatementRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        return StatementRecord.builder()
                .code(rs.getInt(1))
                .item(rs.getString(2))
                .date(rs.getDate(3))
                .value(rs.getString(4))
                .build();
    }
}
