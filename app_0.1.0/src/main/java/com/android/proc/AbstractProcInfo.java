package com.android.proc;

import com.android.Info;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractProcInfo implements Info {
    private String[] wantedFields;  // Must be static in subClass.
    private String filePath;        // Must be static in subClass.
    private Map<String, String> infoMap;

    public AbstractProcInfo() {
        wantedFields = getWantedFields();
        filePath = getFilePath();
        infoMap = new LinkedHashMap<>();
    }

    abstract protected String[] getWantedFields();

    abstract protected String getFilePath();

    private Map<String, String> readFrom(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String s;
        while ((s = in.readLine()) != null) {
            lines.add(s);
        }
        in.close();

        Map<String, String> statMap = new HashMap<>();
        for (String line : lines) {
            String[] vals = line.split(":", 2);
            statMap.put(vals[0], vals[1].trim());
        }

        return statMap;
    }

    private String toJson(Map<String, String> infoMap) throws JSONException {
        JSONObject info = new JSONObject();
        for (String field : wantedFields) {
            info.put(field, infoMap.get(field));
        }
        return info.toString();
    }

    @Override
    public String toString() {
        try {
            return toJson(infoMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public final String update() {
        Map<String, String> statMap = new HashMap<>();
        try {
            statMap = readFrom(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String field : wantedFields) {
            infoMap.put(field, statMap.get(field));
        }
        return this.toString();
    }
}
