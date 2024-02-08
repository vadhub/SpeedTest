package com.abg.speedtest;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloadTest {

    private String fileURL = "";
    private final Listener listener;

    public HttpDownloadTest(String fileURL, Listener listener) {
        this.fileURL = fileURL;
        this.listener = listener;
    }

    private double round(double value) {

        BigDecimal bd;
        try {
            bd = new BigDecimal(value);
        } catch (Exception ex) {
            return 0.0;
        }
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void setInstantDownloadRate(int downloadedByte, double elapsedTime) {

        double instantDownloadRate = 0;
        if (downloadedByte >= 0) {
            instantDownloadRate = round((Double) (((downloadedByte * 8) / (1000 * 1000)) / elapsedTime));
        } else {
            instantDownloadRate = 0.0;
        }

        listener.getSpeed(instantDownloadRate);

    }

    public void run() {

        URL url = null;
        int downloadedByte = 0;
        int responseCode = 0;

        long startTime = System.currentTimeMillis();

        long endTime = 0;
        double downloadElapsedTime = 0;
        try {

            url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpConn.getInputStream());
            responseCode = httpConn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                byte[] buffer = new byte[10240];
                int len = 0;

                while ((len = inputStream.read(buffer)) != -1) {
                    downloadedByte += len;
                    endTime = System.currentTimeMillis();
                    downloadElapsedTime = (endTime - startTime) / 1000.0;
                    setInstantDownloadRate(downloadedByte, downloadElapsedTime);
                    int timeout = 8;
                    if (downloadElapsedTime >= timeout) {
                        break;
                    }
                }

                inputStream.close();
                httpConn.disconnect();
            } else {
                System.out.println("Link not found...");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}