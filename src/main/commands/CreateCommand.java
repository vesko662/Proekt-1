package main.commands;

import main.contracts.Command;
import main.enums.CommandMessages;
import main.exceptions.CommandException;
import main.exceptions.ValidateException;
import main.singletons.FileData;
import main.validate.JsonValidator;

public class CreateCommand implements Command {
    @Override
    public void execute(String args) throws CommandException {
        FileData data = FileData.getInstance();
        String json = data.getFileData();
        String[] parts = args.split(" ", 2);
        if (parts.length<2)
        {
            error(CommandMessages.INVALID_ARGUMENTS);
        }
        String path=parts[0];
        String newValue=parts[1];

        String[] splitPath = path.split("\\.");

        try {

            JsonValidator validate=new JsonValidator(newValue);
            validate.validateValue();

        }catch (ValidateException ve)
        {
            System.out.println(ve.getMessage());
            error(CommandMessages.INVALID_JSON);
        }
        StringBuilder newJson = new StringBuilder(json);

        if ("{}".equals(splitPath[0].trim())) {


            int insertionPoint = newJson.lastIndexOf("}");
            if (newJson.charAt(insertionPoint - 1) == '{') {
                newJson.insert(insertionPoint,splitPath[1]+":"+ newValue);
            } else {
                newJson.insert(insertionPoint, ", " +splitPath[1]+":"+ newValue);
            }
            data.setFileData(newJson.toString());
            return ;
        }


        int startIndex = 1;
        for (int i = 0; i < splitPath.length; i++) {
            String key = splitPath[i];
            int keyIndex = newJson.indexOf("\"" + key + "\"", startIndex);

            if (keyIndex == -1 && i<splitPath.length-1) {
               error(CommandMessages.INVALID_KEY);
           }

            if (i == splitPath.length - 1) {
                int nextBrace = newJson.indexOf("}", startIndex);
                if (newJson.charAt(nextBrace - 1) == '{') {
                    newJson.insert(nextBrace, "\"" + key + "\":" + newValue);
                } else {
                    newJson.insert(nextBrace, ", \"" + key + "\":" + newValue);
                }
                data.setFileData(newJson.toString());
                return ;
            }

            if (newJson.charAt(startIndex) == '{') {
                startIndex++;
            }
        }

        data.setFileData(newJson.toString());
        System.out.println("Successfully created at "+path);
    }

    @Override
    public String getDescription() {
        return "create <path> <string>. Path to the key to create <path> and json as <string>.";
    }

    private void error(CommandMessages commandMessages) throws CommandException {
        throw new CommandException(commandMessages);
    }
}
