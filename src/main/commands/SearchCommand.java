package main.commands;

import main.contracts.Command;
import main.contracts.JSON;
import main.enums.CommandMessages;
import main.exceptions.CommandException;
import main.jsonStructure.*;

import java.util.*;

public class SearchCommand implements Command {
    @Override
    public JSON execute(String args, JSON j) throws CommandException {
        List<JSON> results = new ArrayList<>();
        sObj(j, args, results);
        if (results.isEmpty())
        {
            error(CommandMessages.INVALID_KEY);
        }
        for (JSON found:results) {
            System.out.println(found.stringify());
        }
        return j;
    }

    @Override
    public String getDescription() {
        return "Shows all the matches.";
    }

    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }


    private static void sObj(JSON obj, String key, List<JSON> results) {
        if (((JSONObject)obj).containsKey(key)) {
            results.add(((JSONObject)obj).get(key));
        }
        for (Map.Entry<String, JSON> entry :((JSONObject)obj).get()) {
            JSON value = entry.getValue();
            if (value instanceof JSONObject) {
                sObj(value, key, results);
            } else if (value instanceof JSONArray) {
                sArr((JSONArray) value, key, results);
            }
        }
    }

    private static void sArr(JSONArray array, String key, List<JSON> results) {
        for (int i = 0; i < array.size(); i++) {
            JSON value =array.get(i);
            if (value instanceof JSONObject) {
                sObj(value, key, results);
            } else if (value instanceof JSONArray) {
                sArr((JSONArray) value, key, results);
            }
        }
    }
}
