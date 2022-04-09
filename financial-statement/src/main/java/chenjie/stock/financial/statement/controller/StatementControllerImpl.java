package chenjie.stock.financial.statement.controller;

import chenjie.stock.common.domain.StatementRecord;
import chenjie.stock.common.domain.StatementType;
import chenjie.stock.financial.statement.domain.ReturnCode;
import chenjie.stock.financial.statement.domain.StatementRequest;
import chenjie.stock.financial.statement.domain.StatementResponse;
import chenjie.stock.financial.statement.service.StatementLoaderService;
import chenjie.stock.financial.statement.service.StatementQueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Lazy
public class StatementControllerImpl implements StatementController {

    @Autowired
    private StatementLoaderService loaderService;

    @Autowired
    private StatementQueryService queryService;

    @Override
    public StatementResponse loadFromFiles(String sinceDate, List<String> files) {
        int returnCode = ReturnCode.SUCCESS;
        if (CollectionUtils.isEmpty(files)) {
            returnCode = ReturnCode.BAD_REQUEST;
        } else {
            log.info("Received request to load data from files of size {} since date {}", files.size(), sinceDate);
            for (String file : files) {
                try {
                    loaderService.loadDataFromCsvFile(file, sinceDate);
                    log.info("Finished loading file {}", file);
                } catch (IOException | ParseException e) {
                    log.error("Failed to load from file {}", file, e);
                    returnCode = ReturnCode.INTERNAL_ERROR;
                }
            }
        }
        return StatementResponse.builder()
                .returnCode(returnCode)
                .message(ReturnCode.getMessage(returnCode))
                .build();
    }

    @Override
    public StatementResponse loadFromDirectory(String sinceDate, String directory) {
        int returnCode = ReturnCode.SUCCESS;
        if (StringUtils.isEmpty(directory)) {
            returnCode = ReturnCode.BAD_REQUEST;
        } else {
            log.info("Received request to load data from files in the direcotry {} since date {}", directory, sinceDate);
            try {
                loaderService.loadDataFromFilesInDirectory(directory, sinceDate);
                log.info("Finished loading data from directory {}", directory);
            } catch (IOException e) {
                log.error("Failed to list files from directory {}", directory, e);
                returnCode = ReturnCode.INTERNAL_ERROR;
            }
        }
        return StatementResponse.builder()
                .returnCode(returnCode)
                .message(ReturnCode.getMessage(returnCode))
                .build();
    }

    @Override
    public String getValue(String type, String code, String item, String date) {
        String value = null;
        StatementType statementType = StatementType.valueOf(type);
        if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(item) && StringUtils.isNotEmpty(date)) {
            value = queryService.getValue(statementType, code, item, date);
        }
        return value;
    }

    @Override
    public StatementResponse getValue(String type, StatementRequest request) {
        StatementType statementType = StatementType.valueOf(type);
        List<StatementRecord> records = queryService.getValues(statementType, request.getCodes(), request.getItems(), request.getFromDate(), request.getToDate());
        return StatementResponse.builder()
                .returnCode(ReturnCode.SUCCESS)
                .message(ReturnCode.getMessage(ReturnCode.SUCCESS))
                .result(records)
                .build();
    }


    @Override
    public StatementResponse getValue(String type) {
        StatementType statementType = StatementType.valueOf(type);
        List<StatementRecord> records = queryService.getAllValues(statementType);
        return StatementResponse.builder()
                .returnCode(ReturnCode.SUCCESS)
                .message(ReturnCode.getMessage(ReturnCode.SUCCESS))
                .result(records)
                .build();
    }
}
