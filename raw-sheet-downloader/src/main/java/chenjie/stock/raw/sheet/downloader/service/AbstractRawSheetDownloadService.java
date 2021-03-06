package chenjie.stock.raw.sheet.downloader.service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class AbstractRawSheetDownloadService implements RawSheetDownloadService {

    protected abstract String getURL();

    protected abstract String getFilePath();

    private static final int BUFFER_SIZE = 4096;
    private static final int RETRY_COUNT = 3;

    @Override
    public void downloadSheet(String code) throws IOException {
        String url = getURL().replace("XXXXXX", code);
        HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
        int responseCode = httpConn.getResponseCode();
        int count = 0;
        while (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("Server replied HTTP code: " + responseCode);
            httpConn.disconnect();
            if (++count <= 3) {
                System.out.println("Retry download for " + code + " for the " + count + " time");
                httpConn = (HttpURLConnection) new URL(url).openConnection();
                responseCode = httpConn.getResponseCode();
            } else {
                break;
            }
        }

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 9);
                }
            } else {
                // extracts file name from URL
                fileName = url.substring(url.lastIndexOf("/") + 1);
            }

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = getFilePath() + File.separator + fileName;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

            int ch;
            while ((ch = reader.read()) != -1) {
                writer.write(ch);
            }
            reader.close();
            writer.close();
            System.out.println("File downloaded: " + fileName);
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    @Override
    public void downloadAllSheets() throws IOException {
        InputStream inputstream = Thread.currentThread().getContextClassLoader().getResourceAsStream("code");
        assert inputstream != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputstream));
        List<String> codes = new ArrayList<>();
        String code;
        while ((code = reader.readLine()) != null) {
            codes.add(code);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(9);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int startIndex = i * 500;
            int endIndex = Math.min((i + 1) * 500, codes.size());
            List<String> subList = codes.subList(startIndex, endIndex);
            Callable<String> task = () -> {
                for (String code1 : subList) {
                    downloadSheet(code1);
                }
                return "Finished downloading sheet from " + subList.get(0) + "to " + subList.get(subList.size() - 1);
            };
            tasks.add(task);
        }
        try {
            List<Future<String>> results = executorService.invokeAll(tasks);
            for (Future<String> future : results) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println("All files are downloaded.");
    }
}
