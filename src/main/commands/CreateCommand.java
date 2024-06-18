package main.commands;

import main.contracts.Command;
import main.contracts.JSON;
import main.enums.CommandMessages;
import main.exceptions.CommandException;
import main.exceptions.ValidateException;
import main.jsonStructure.*;
import main.parcer.JSONParser;

public class CreateCommand implements Command {
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

        JSON newValue;
        try {
            JSONParser parser = new JSONParser();
            newValue = parser.parse(jsonString);
        } catch (ValidateException e) {
            System.out.println(e.getMessage());
            throw new CommandException(CommandMessages.INVALID_JSON);
        }

        String[] keys = path.split("\\.");
        if (!cObj((JSONObject) j, keys, 0, newValue)) {
            error(CommandMessages.INVALID_PATH);
        }

        return j;
    }

    @Override
    public String getDescription() {
        return "create <path> <string>. Path to the key to create <path> and json as <string>.";
    }

    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }
    private boolean cObj(JSONObject obj, String[] keys, int index, JSON newValue) throws CommandException {
        if (index == keys.length - 1) {
            if (obj.containsKey(keys[index])) {
                error(CommandMessages.ELEMENT_ALREADY_EXISTS);
                return false;
            } else {
                obj.put(keys[index], newValue);
                return true;
            }
        }

        JSON value = obj.get(keys[index]);
        if (value == null) {
            JSONObject newObj = new JSONObject();
            obj.put(keys[index], newObj);
            return cObj(newObj, keys, index + 1, newValue);
        }

        if (value instanceof JSONObject) {
            return cObj((JSONObject) value, keys, index + 1, newValue);
        } else if (value instanceof JSONArray) {
            try {
                int arrayIndex = Integer.parseInt(keys[index + 1]);
                return cArr((JSONArray) value, keys, index + 1, arrayIndex, newValue);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean cArr(JSONArray array, String[] keys, int index, int arrayIndex, JSON newValue) throws CommandException {
        if (index == keys.length - 1) {
            if (arrayIndex < array.size()) {
                error(CommandMessages.ELEMENT_ALREADY_EXISTS);
                return false;
            } else {
                while (array.size() <= arrayIndex) {
                    array.add(null);
                }
                array.set(arrayIndex, newValue);
                return true;
            }
        }

        JSON value = array.get(arrayIndex);
        if (value == null) {
            JSONObject newObj = new JSONObject();
            array.set(arrayIndex, newObj);
            return cObj(newObj, keys, index + 1, newValue);
        }

        if (value instanceof JSONObject) {
            return cObj((JSONObject) value, keys, index + 1, newValue);
        } else if (value instanceof JSONArray) {
            try {
                int nextArrayIndex = Integer.parseInt(keys[index + 1]);
                return cArr((JSONArray) value, keys, index + 1, nextArrayIndex, newValue);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}