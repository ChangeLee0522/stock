package chenjie.stock.common.infrastructure.dao.statement;

import chenjie.stock.common.domain.StatementRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class AbstractStatementDaoImpl implements StatementDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    protected abstract String getTableName();

    @Override
    public void save(StatementRecord statementRecord) {
        String sql = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, statementRecord.getCode(), statementRecord.getItem(), statementRecord.getDate(), statementRecord.getValue());
        log.info("Successfully insert into table {} for record {}.", getTableName(), statementRecord);
    }

    @Override
    public void save(List<StatementRecord> statementRecords) {
        String sql = "INSERT INTO " + getTableName() + " VALUES (?, ?, ?, ?);";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StatementRecord record = statementRecords.get(i);
                ps.setString(1, record.getCode());
                ps.setString(2, record.getItem());
                ps.setDate(3, record.getDate());
                ps.setFloat(4, record.getValue());
            }

            @Override
            public int getBatchSize() {
                return statementRecords.size();
            }
        });
        log.info("Successfully insert into table {} for {} records.", getTableName(), statementRecords.size());
    }

    @Override
    public Float get(String code, String item, String date) {
        String sql = "SELECT value FROM " + getTableName() + " WHERE code = \"" + code + "\" AND item = \"" + item + "\" AND date = \"" + date + "\";";
        return jdbcTemplate.queryForObject(sql, Float.class);
    }

    @Override
    public List<StatementRecord> get(List<String> codes, List<String> items, String fromDate, String toDate) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + getTableName());
        if (CollectionUtils.isNotEmpty(codes)) {
            appendCondition(sql);
            List<String> quotedCodes = addQuotes(codes);
            sql.append("code IN (").append(String.join(",", quotedCodes)).append(")");
        }
        if (CollectionUtils.isNotEmpty(items)) {
            appendCondition(sql);
            List<String> quotedItems = addQuotes(items);
            sql.append("item IN (").append(String.join(",", quotedItems)).append(")");
        }
        if (StringUtils.isNotEmpty(fromDate)) {
            appendCondition(sql);
            sql.append("date >= ").append("\"").append(fromDate).append("\"");
        }
        if (StringUtils.isNotEmpty(toDate)) {
            appendCondition(sql);
            sql.append("date <= ").append("\"").append(toDate).append("\"");
        }
        List<StatementRecord> ret = jdbcTemplate.query(sql.toString(), new StatementRecordRowMapper());
        log.info("Successfully query {} records from table {}.", ret.size(), getTableName());
        return ret;
    }

    private List<String> addQuotes(List<String> strings) {
        List<String> ret = new ArrayList<>(strings.size());
        strings.forEach(s -> ret.add("\"" + s + "\""));
        return ret;
    }

    private void appendCondition(StringBuilder sql) {
        if (sql.indexOf("WHERE") == -1) {
            sql.append(" WHERE ");
        } else {
            sql.append(" AND ");
        }
    }

    @Override
    public List<StatementRecord> getAll() {
        return get(null, null, null, null);
    }

}
