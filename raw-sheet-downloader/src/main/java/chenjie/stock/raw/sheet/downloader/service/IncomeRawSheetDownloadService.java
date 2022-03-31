package chenjie.stock.raw.sheet.downloader.service;

public class IncomeRawSheetDownloadService extends AbstractRawSheetDownloadService {
    private static final String INCOME_URL = "https://quotes.money.163.com/service/lrb_XXXXXX.html";

    @Override
    protected String getURL() {
        return INCOME_URL;
    }

    @Override
    protected String getFilePath() {
        return System.getProperty("income_sheet_location");
    }
}
