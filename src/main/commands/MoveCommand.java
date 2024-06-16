package main.commands;

import main.contracts.Command;
import main.contracts.JSON;
import main.enums.CommandMessages;
import main.exceptions.CommandException;
import main.jsonStructure.*;

public class MoveCommand implements Command {
    private JSON element=null;
    @Override
    public JSON execute(String args, JSON j) throws CommandException {
        if (args.isEmpty()) {
            error(CommandMessages.INVALID_ARGUMENTS);
        }

        String[] parts = args.split(" ", 2);
        if (parts.length < 2) {
            error(CommandMessages.INVALID_ARGUMENTS);
        }

        String fromPath = parts[0];
        String toPath = parts[1];

        if (!mObj((JSONObject) j, fromPath.split("\\."), 0)) {
            error(CommandMessages.INVALID_PATH);
        }
        new SetCommand().execute(fromPath+" "+"\" \"",j);
        new SetCommand().execute(toPath+" "+element.stringify(),j);

        return j;
    }
    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }
    @Override
    public String getDescription() {
        return "move <from> <to> move the data from given path <from> to the other path <to>";
    }
    private boolean mObj(JSONObject obj, String[] keys, int index) {
        if (index == keys.length - 1) {
            if (obj.containsKey(keys[index])) {
                element=obj.get(keys[index]);
                return true;
            } else {
                return false;
            }
        }

        JSON value = obj.get(keys[index]);
        if (value instanceof JSONObject) {
            return mObj((JSONObject) value, keys, index + 1);
        } else if (value instanceof JSONArray) {
            try {
                int arrayIndex = Integer.parseInt(keys[index + 1]);
                return mArr((JSONArray) value, keys, index + 1, arrayIndex);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean mArr(JSONArray array, String[] keys, int index, int arrayIndex) {
        if (index == keys.length - 1) {
            if (arrayIndex < array.size()) {
                element=  array.get(arrayIndex);
                return true;
            } else {
                return false;
            }
        }

        JSON value = array.get(arrayIndex);
        if (value instanceof JSONObject) {
            return mObj((JSONObject) value, keys, index + 1);
        } else if (value instanceof JSONArray) {
            try {
                int nextArrayIndex = Integer.parseInt(keys[index + 1]);
                return mArr((JSONArray) value, keys, index + 1, nextArrayIndex);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
