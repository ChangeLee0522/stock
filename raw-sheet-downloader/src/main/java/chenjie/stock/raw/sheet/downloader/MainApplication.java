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
        RawSheetDownloadService service = factory.getService(SheetType.BALANCE);
        service.downloadAllSheets();
    }
}
