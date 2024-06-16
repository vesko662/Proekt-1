package main.commands;

import main.contracts.Command;
import main.contracts.JSON;
import main.enums.CommandMessages;
import main.exceptions.CommandException;
import main.exceptions.ValidateException;
import main.jsonStructure.*;
import main.parcer.JSONParser;

public class SetCommand implements Command {
    @Override
    public JSON execute(String args, JSON j) throws CommandException {
        if (args.isEmpty()) {
            error(CommandMessages.INVALID_ARGUMENTS);
        }

        String[] parts = args.split(" ", 2);
        if (parts.length < 2) {
            error(CommandMessages.INVALID_ARGUMENTS);
        }

        String path = parts[0];
        String jsonString = parts[1];

        JSON newValue=null;
        try {
            JSONParser parser = new JSONParser();
            newValue = parser.parse(jsonString);
        } catch (ValidateException e) {
            System.out.println(e.getMessage());
            error(CommandMessages.INVALID_JSON);
        }

        String[] keys = path.split("\\.");
        if (!sObj((JSONObject) j, keys, 0, newValue)) {
            error(CommandMessages.INVALID_PATH);
        }

        return j;
    }


    @Override
    public String getDescription() {
        return "set <path> <string>. Path to the key <path> and json as <string>.";
    }

    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }

    private boolean sObj(JSONObject obj, String[] keys, int index, JSON newValue) {
        if (index == keys.length - 1) {
            if (obj.containsKey(keys[index])) {
                obj.put(keys[index], newValue);
                return true;
            } else {
                return false;
            }
        }

        JSON value = obj.get(keys[index]);
        if (value instanceof JSONObject) {
            return sObj((JSONObject) value, keys, index + 1, newValue);
        } else if (value instanceof JSONArray) {
            try {
                int arrayIndex = Integer.parseInt(keys[index + 1]);
                return sArr((JSONArray) value, keys, index + 1, arrayIndex, newValue);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean sArr(JSONArray array, String[] keys, int index, int arrayIndex, JSON newValue) {
        if (index == keys.length - 1) {
            if (arrayIndex < array.size()) {
                array.set(arrayIndex, newValue);
                return true;
            } else {
                return false;
            }
        }

        JSON value = array.get(arrayIndex);
        if (value instanceof JSONObject) {
            return sObj((JSONObject) value, keys, index + 1, newValue);
        } else if (value instanceof JSONArray) {
            try {
                int nextArrayIndex = Integer.parseInt(keys[index + 1]);
                return sArr((JSONArray) value, keys, index + 1, nextArrayIndex, newValue);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
