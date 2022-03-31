package chenjie.stock.raw.sheet.downloader.service;

public class BalanceRawSheetDownloadService extends AbstractRawSheetDownloadService {
    private static final String BALANCE_URL = "https://quotes.money.163.com/service/zcfzb_XXXXXX.html";

    @Override
    protected String getURL() {
        return BALANCE_URL;
    }

    @Override
    protected String getFilePath() {
        return System.getProperty("balance_sheet_location");
    }
}
