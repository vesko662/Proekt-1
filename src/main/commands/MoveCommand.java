package main.commands;

import main.contracts.Command;
import main.enums.CommandMessages;
import main.exeptions.CommandException;
import main.singletons.FileData;

public class MoveCommand implements Command {
    @Override
    public void execute(String args) throws CommandException {
        FileData data = FileData.getInstance();
        String json=data.getFileData();
        String[] paths=args.split(" ");
        String pathFrom=paths[0];
        String pathTo=paths[1];

        String[] keys = pathFrom.split("\\.");
        int start = 0;

        for (String key : keys) {
            String searchKey = "\"" + key + "\":";
            start = json.indexOf(searchKey, start);
            if (start == -1) {
                error(CommandMessages.INVALID_KEY);
            }
            start += searchKey.length();
        }

        int removeStart = start;
        int removeEnd = findEndOfValue(json, start);

        if (removeEnd < json.length() && json.charAt(removeEnd) == ',') {
            removeEnd++;
        }

        String before = json.substring(0, removeStart);
        String after = json.substring(removeEnd);
        String element=json.substring(removeStart,removeEnd);

        if (before.trim().endsWith(",") && (after.startsWith("}") || after.isEmpty())) {
            before = before.substring(0, before.lastIndexOf(","));
        }
        before = before.concat("\" \"");

        data.setFileData(before+ after);
      new SetCommand().execute(pathTo+" "+element);

    }

    private  int findEndOfValue(String json, int start) {
        int depth = 0;
        boolean inQuotes = false;
        for (int i = start; i < json.length(); i++)
        {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\'))
            {
                inQuotes = !inQuotes;
            } else if (!inQuotes)
            {
                if (c == '{' || c == '[')
                {
                    depth++;
                } else if (c == '}' || c == ']')
                {
                    depth--;
                }
                if ((c == ',' && depth == 0) || depth < 0)
                {
                    return i;
                }
            }
        }
        return json.length();
    }
    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }
    @Override
    public String getDescription() {
        return null;
    }
}
