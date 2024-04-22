package main.commands;

import main.contracts.Command;
import main.singletons.FileData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommand implements Command {
    @Override
    public void execute(String args) {
        FileData data = FileData.getInstance();
        String json=data.getFileData();
        data.setFileData(deleteElement(json,args));
    }


    public  String deleteElement(String json, String fullPath) {
        String[] keys = fullPath.split("\\.");
        int start = 0;

        for (String key : keys) {
            String searchKey = "\"" + key + "\":";
            start = json.indexOf(searchKey, start);
            if (start == -1) {
                return "Key not found: " + key; //TODO error handling
            }
            start += searchKey.length();
        }

        int removeStart = start;
        while (json.charAt(removeStart - 1) != '{' && json.charAt(removeStart - 1) != ',') {
            removeStart--;
        }

        int removeEnd = findEndOfValue(json, start);

        if (removeEnd < json.length() && json.charAt(removeEnd) == ',') {
            removeEnd++;
        }

        String before = json.substring(0, removeStart);
        String after = json.substring(removeEnd);

        if (before.trim().endsWith(",") && (after.startsWith("}") || after.isEmpty())) {
            before = before.substring(0, before.lastIndexOf(","));
        }

        return before + after;
    }

    private  int findEndOfValue(String json, int start) {
        int depth = 0;
        boolean inQuotes = false;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (!inQuotes) {
                if (c == '{' || c == '[') {
                    depth++;
                } else if (c == '}' || c == ']') {
                    depth--;
                }
                if ((c == ',' && depth == 0) || depth < 0) {
                    return i;
                }
            }
        }
        return json.length();
    }


    @Override
    public String getDescription() {
        return null;
    }
}
