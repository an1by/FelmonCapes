package ru.aniby.felmoncapes.utils;

import org.json.JSONException;
import org.json.JSONObject;
import ru.aniby.felmoncapes.FelmonCapes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class Capes {
    public static HashMap<String, String> capeList = new HashMap<>();
    public static void initCapeList() {
        try {
            URL url = new URL(FelmonCapes.config.getCapeList());
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();

            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            JSONObject capesObject = new JSONObject(result.toString());
            for (String key : capesObject.keySet()) {
                capeList.put(key, capesObject.getString(key));
            }
        } catch (Exception exception) {
            FelmonCapes.LOGGER.error("Cape list init error!");
            exception.printStackTrace();
        }
    }
    public static String getCapeURL(String capeName) {
        return capeName == null ? "" : capeList.get(capeName);
    }

    public static String getWithNickname(String nickname) {
        try {
            URL url = new URL(FelmonCapes.config.getRequestURL() + "?username=" + nickname);
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();

            StringBuilder result = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
            String cape;
            try {
                cape = new JSONObject(result.toString()).getString("cape");
            } catch (JSONException exception) {
                cape = null;
            }
            FelmonCapes.LOGGER.info("cape: " + cape + " | url: " + getCapeURL(cape));
            return getCapeURL(cape);
        } catch (Exception ignored) {
            return "";
        }
    }
}