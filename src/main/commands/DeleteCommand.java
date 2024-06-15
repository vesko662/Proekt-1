package main.commands;

import main.contracts.Command;
import main.contracts.JSON;
import main.enums.CommandMessages;
import main.exceptions.CommandException;
import main.jsonStructure.*;

public class DeleteCommand implements Command {
    @Override
    public JSON execute(String args, JSON j) throws CommandException {
        if (args.isEmpty())
        {
            error(CommandMessages.INVALID_ARGUMENTS);
        }
        String[] path=args.split("\\.");

        if (!dObj((JSONObject) j, path, 0)) {
            error(CommandMessages.INVALID_PATH);
        }
        return j;
    }


    @Override
    public String getDescription() {
        return "deletes the element at the given <path>";
    }

    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }
    private boolean dObj(JSONObject obj, String[] keys, int index) {
        if (index == keys.length - 1) {
            if (obj.containsKey(keys[index])) {
                obj.remove(keys[index]);
                return true;
            } else {
                return false;
            }
        }

        JSON value = obj.get(keys[index]);
        if (value instanceof JSONObject) {
            return dObj((JSONObject) value, keys, index + 1);
        } else if (value instanceof JSONArray) {
            try {
                int arrayIndex = Integer.parseInt(keys[index + 1]);
                return dArr((JSONArray) value, keys, index + 1, arrayIndex);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean dArr(JSONArray array, String[] keys, int index, int arrayIndex) {
        if (index == keys.length - 1) {
            if (arrayIndex < array.size()) {
                array.remove(arrayIndex);
                return true;
            } else {
                return false;
            }
        }

        JSON value = array.get(arrayIndex);
        if (value instanceof JSONObject) {
            return dObj((JSONObject) value, keys, index + 1);
        } else if (value instanceof JSONArray) {
            try {
                int nextArrayIndex = Integer.parseInt(keys[index + 1]);
                return dArr((JSONArray) value, keys, index + 1, nextArrayIndex);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}