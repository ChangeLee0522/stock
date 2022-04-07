package chenjie.stock.raw.sheet.downloader;

import chenjie.stock.raw.sheet.downloader.domain.SheetType;
import chenjie.stock.raw.sheet.downloader.service.RawSheetDownloadService;
import chenjie.stock.raw.sheet.downloader.service.RawSheetDownloadServiceFactory;

import java.io.IOException;
import java.io.InputStream;

public class MainApplication {
    public static void main(String[] args) throws IOException {
        String propertiesFile = "application.properties";
        InputStream inputstream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFile);
        System.getProperties().load(inputstream);
        RawSheetDownloadServiceFactory factory = new RawSheetDownloadServiceFactory();
        RawSheetDownloadService service1 = factory.getService(SheetType.BALANCE);
//        service1.downloadSheet("603619");
//        service1.downloadAllSheets();
        RawSheetDownloadService service2 = factory.getService(SheetType.CASH_FLOW);
//        service2.downloadSheet("301008");
//        service2.downloadAllSheets();
        RawSheetDownloadService service3 = factory.getService(SheetType.INCOME);
//        service3.downloadAllSheets();
    }
}
