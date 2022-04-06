package chenjie.stock.financial.statement.controller;

import chenjie.stock.financial.statement.domain.ReturnCode;
import chenjie.stock.financial.statement.domain.StatementResponse;
import chenjie.stock.financial.statement.service.StatementLoaderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
@Lazy
public class StatementControllerImpl implements StatementController {

    @Autowired
    private StatementLoaderService service;

    @Override
    public StatementResponse loadFromFiles(String sinceDate, List<String> files) {
        int returnCode = ReturnCode.SUCCESS;
        if (CollectionUtils.isEmpty(files)) {
            returnCode = ReturnCode.BAD_REQUEST;
        } else {
            log.info("Received request to load data from files of size {} since date {}", files.size(), sinceDate);
            for (String file : files) {
                try {
                    service.loadDataFromCsvFile(file, sinceDate);
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
        if (StringUtils.isEmpty(sinceDate)) {
            returnCode = ReturnCode.BAD_REQUEST;
        } else {
            log.info("Received request to load data from files in the direcotry {} since date {}", directory, sinceDate);
            try {
                service.loadDataFromFilesInDirectory(directory, sinceDate);
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
}
