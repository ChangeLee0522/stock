package chenjie.stock.raw.sheet.downloader.service;

public class CashFlowRawSheetIncomeService extends AbstractRawSheetDownloadService {
    private static final String CASH_FLOW_URL = "https://quotes.money.163.com/service/xjllb_XXXXXX.html";

    @Override
    protected String getURL() {
        return CASH_FLOW_URL;
    }

    @Override
    protected String getFilePath() {
        return System.getProperty("cash_flow_sheet_location");
    }
}
