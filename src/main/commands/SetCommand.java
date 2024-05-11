package main.commands;

import main.contracts.Command;
import main.enums.CommandMessages;
import main.exeptions.CommandException;
import main.exeptions.ValidateException;
import main.singletons.FileData;
import main.validate.JsonValidator;

public class SetCommand implements Command {
    @Override
    public void execute(String args) throws CommandException {
        FileData data = FileData.getInstance();
        String json=data.getFileData().trim();
        String[] ar=args.split(" ",2);
        if (ar.length<2)
        {
            error(CommandMessages.INVALID_ARGUMENTS);
        }
        String path=ar[0];
        String newValue=ar[1];

        try {

                JsonValidator validate=new JsonValidator(newValue);
                validate.validateValue();

        }catch (ValidateException ve)
       {
            System.out.println(ve.getMessage());
           error(CommandMessages.INVALID_JSON);
      }


        String[] splitPath = path.split("\\.");
        String modJson = json;
        int startIndex = 0;

        for (int i = 0; i < splitPath.length; i++) {
            String key = splitPath[i];
            startIndex = modJson.indexOf("\"" + key + "\"", startIndex);
            if (startIndex == -1) {
                error(CommandMessages.INVALID_PATH);
            }
            startIndex += key.length() + 2;

            int colonIndex = modJson.indexOf(":", startIndex);
            if (colonIndex == -1) {
                error(CommandMessages.INVALID_STRUCTURE);
            }


            int valueStart = findStartOfValue(modJson, colonIndex + 1);
            int valueEnd = findEndOfValue(modJson, valueStart);

            if (i == splitPath.length - 1) {
                modJson = modJson.substring(0, valueStart) + newValue + modJson.substring(valueEnd);
            } else {
                startIndex = valueStart;
            }
        }

        data.setFileData(modJson);
    }

    private  int findStartOfValue(String json, int start) {
        while (start < json.length() && (json.charAt(start) == ' ' || json.charAt(start) == '\t')) {
            start++;
        }
        return start;
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
                 }
                else if (c == '}' || c == ']')
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
    @Override
    public String getDescription() {
        return "set <path> <string>. Path to the key <path> and json as <string>.";
    }

    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }
}
