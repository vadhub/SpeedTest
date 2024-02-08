package com.abg.speedtest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ping {

    public static double getPingTime(String ipAddress) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("ping -c 1 " + ipAddress);
            BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                if (line.contains("time=")) {
                    Pattern pattern = Pattern.compile("time=(\\d+(\\.\\d+)?)");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String pingTimeStr = matcher.group(1);
                        return Double.parseDouble(pingTimeStr);
                    }
                }
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // -1 если не удалось рассчитать пинг
    }
}