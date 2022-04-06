package chenjie.stock.financial.statement.service;

import chenjie.stock.common.application.statement.StatementService;
import chenjie.stock.common.application.statement.StatementServiceFactory;
import chenjie.stock.common.domain.StatementRecord;
import chenjie.stock.common.domain.StatementType;
import chenjie.stock.common.infrastructure.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class StatementLoaderService {

    @Autowired
    private StatementServiceFactory factory;

    private static final String CSV_DELIMITER = ",";

    public void loadDataFromFilesInDirectory(String directory, String sinceDate) throws IOException {
        Files.list(Paths.get(directory)).forEach(path -> {
            try {
                loadDataFromCsvFile(path.toFile().getAbsolutePath(), sinceDate);
            } catch (IOException | ParseException e) {
                log.error("Failed to load data from directory {}", directory);
            }
        });
    }

    public void loadDataFromCsvFile(String filePath, String sinceDate) throws IOException, ParseException {
        File file = new File(filePath);
        String fileName = file.getName();
        String code = getCodeFromFileName(fileName);
        if (code == null) {
            log.error("Can't find code from file name. Stop here.");
            return;
        }
        List<String[]> fileContents = getFileContents(file);
        List<String> dates = Arrays.asList(fileContents.get(0));
        if (StringUtils.isNotEmpty(sinceDate)) {
            dates = getDatesSubList(sinceDate, dates);
        }
        List<String[]> itemWithValuesList = fileContents.subList(1, fileContents.size());
        List<StatementRecord> records = getStatementRecords(code, dates, itemWithValuesList);
        String statementType = getStatementTypeFromFileName(fileName);
        StatementService statementService = factory.getStatementService(statementType);
        statementService.save(records);
    }



    private List<String> getDatesSubList(String sinceDate, List<String> dates) throws ParseException {
        int endDateIndex = dates.size();
        for (int i = 1; i < dates.size(); i++) {
            if (DateTimeUtil.compareDates(dates.get(i), sinceDate) < 0) {
                endDateIndex = i;
                break;
            }
        }
        dates = dates.subList(0, endDateIndex);
        return dates;
    }

    private List<StatementRecord> getStatementRecords(String code, List<String> dates, List<String[]> itemWithValuesList) {
        List<StatementRecord> records = new ArrayList<>();
        for (String[] itemWithValues : itemWithValuesList) {
            String item = itemWithValues[0];
            log.info("Loading for item {} of code {}", item, code);
            for (int i = 1; i < dates.size(); i++) {
                if(itemWithValues[i].equals("--")) {
                    continue;
                }
                StatementRecord record = StatementRecord.builder()
                        .code(code)
                        .item(item)
                        .date(Date.valueOf(dates.get(i)))
                        .value(itemWithValues[i])
                        .build();
                records.add(record);
            }
        }
        log.info("Got {} statement records.", records.size());
        return records;
    }

    private List<String[]> getFileContents(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        List<String[]> fileContents = new ArrayList<>();
        while (StringUtils.isNotEmpty((line = reader.readLine()))) {
            if (!line.trim().isEmpty()) {
                String[] lineContents = line.split(CSV_DELIMITER);
                fileContents.add(lineContents);
            }
        }
        log.info("Read {} lines from file {}.", fileContents.size(), file);
        return fileContents;
    }

    private String getStatementTypeFromFileName(String fileName) {
        if (fileName.contains("zcfzb")) {
            log.info("The type of {} is balance.", fileName);
            return StatementType.balance.name();
        } else if (fileName.contains("lrb")) {
            log.info("The type of {} is income.", fileName);
            return StatementType.income.name();
        } else if (fileName.contains("xjllb")) {
            log.info("The type of {} is cash_flow.", fileName);
            return StatementType.cash_flow.name();
        } else {
            log.error("No type found from file {}.", fileName);
            return null;
        }
    }

    private String getCodeFromFileName(String file) {
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(file);
        if (matcher.find()) {
            log.info("Got code {} from file {}.", matcher.group(0), file);
            return matcher.group(0);
        }
        return null;
    }
}
