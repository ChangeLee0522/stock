package chenjie.stock.raw.sheet.downloader.service;

import java.io.IOException;

public interface RawSheetDownloadService {
    void downloadSheet(String code) throws IOException;
    void downloadAllSheets() throws IOException;
}
