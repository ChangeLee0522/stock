package chenjie.stock.raw.sheet.downloader;

import chenjie.stock.raw.sheet.downloader.service.BalanceRawSheetDownloadService;
import chenjie.stock.raw.sheet.downloader.service.CashFlowRawSheetIncomeService;
import chenjie.stock.raw.sheet.downloader.service.IncomeRawSheetDownloadService;

import java.io.IOException;
import java.io.InputStream;

public class MainApplication {
    public static void main(String[] args) throws IOException {
        String propertiesFile = "application.properties";
        InputStream inputstream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
        System.getProperties().load(inputstream);
        BalanceRawSheetDownloadService balanceRawSheetDownloadService = new BalanceRawSheetDownloadService();
        balanceRawSheetDownloadService.downloadAllSheets();
        CashFlowRawSheetIncomeService cashFlowRawSheetIncomeService = new CashFlowRawSheetIncomeService();
        cashFlowRawSheetIncomeService.downloadAllSheets();
        IncomeRawSheetDownloadService incomeRawSheetDownloadService = new IncomeRawSheetDownloadService();
        incomeRawSheetDownloadService.downloadAllSheets();
    }
}
