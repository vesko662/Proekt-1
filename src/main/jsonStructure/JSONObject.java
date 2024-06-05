package main.jsonStructure;

import main.contracts.JSON;

import java.util.*;

public class JSONObject implements JSON {
    private final Map<String, JSON> map;

    public JSONObject() {
        this.map = new HashMap<>();
    }

    public void put(String key, JSON value) {
        map.put(key, value);
    }

    public JSON get(String key) {
        return map.get(key);
    }
    public Set<Map.Entry<String, JSON>> get() {
        return map.entrySet();
    }
    public JSON remove(String key) {
        return map.remove(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public String stringify(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = " ".repeat(indentLevel * 4);
        sb.append("{\n");
        int count = 0;
        for (Map.Entry<String, JSON> entry : map.entrySet()) {
            if (count > 0) {
                sb.append(",\n");
            }
            sb.append(indent).append("    \"").append(entry.getKey()).append("\": ").append(entry.getValue().stringify(indentLevel + 1));
            count++;
        }
        sb.append("\n").append(indent).append("}");
        return sb.toString();
    }
}
