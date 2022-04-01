package chenjie.stock.raw.sheet.downloader.service;

import chenjie.stock.raw.sheet.downloader.domain.SheetType;

public class RawSheetDownloadServiceFactory {
    public RawSheetDownloadService getService(SheetType type) {
        if (SheetType.BALANCE.equals(type)) {
            return new BalanceRawSheetDownloadService();
        } else if (SheetType.CASH_FLOW.equals(type)) {
            return new CashFlowRawSheetIncomeService();
        } else if (SheetType.INCOME.equals(type)) {
            return new IncomeRawSheetDownloadService();
        } else {
            return null;
        }
    }
}
