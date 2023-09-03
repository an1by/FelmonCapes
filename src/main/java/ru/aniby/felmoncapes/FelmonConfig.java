package ru.aniby.felmoncapes;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class FelmonConfig {
    private JSONObject json = new JSONObject();
    private final File file;

    public FelmonConfig(String fileName) {
        this.file = FabricLoader.getInstance().getConfigDir().resolve(fileName).toFile();
    }

    public boolean load() {
        if (!file.exists()) {
            this.json = getDefaultJSON();
            return save();
        }

        try {
            String data = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            this.json = new JSONObject(data);
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public JSONObject getDefaultJSON() {
        JSONObject defaultJson = new JSONObject();
        defaultJson.put("request_url", "https://api.aniby.ru/minecraft/cape");
        defaultJson.put("cape_list", "https://repository.aniby.ru/minecraft/capes.json");
        return defaultJson;
    }

    public boolean save() {
        try {
            String result = json.toString();
            try (FileOutputStream out = new FileOutputStream(file, false)) {
                out.write(result.getBytes(StandardCharsets.UTF_8));
            }
            return true;
        } catch (Exception e) {
            FelmonCapes.LOGGER.error("Error saving config: {}", e.getMessage());
            return false;
        }
    }

    public String getRequestURL() {
        return json.getString("request_url");
    }

    public String getCapeList() {
        return json.getString("cape_list");
    }
}