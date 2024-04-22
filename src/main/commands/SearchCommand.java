package main.commands;

import main.contracts.Command;
import main.singletons.FileData;

public class SearchCommand implements Command {
    @Override
    public void execute(String args) {
        FileData data = FileData.getInstance();
        String json=data.getFileData().trim();




        String[] keys = json.split("\"" + args + "\"\\s*:\\s*");
        if (keys.length > 1) {
            for (int i = 1; i < keys.length; i++) {
                String value = keys[i];
                int endIdx = findEndOfValue(value);
                System.out.println(value.substring(0, endIdx));
            }
        } else {
            System.out.println("Key not found: " + args);//TODO error handling
        }
    }
    private int findEndOfValue(String value) {
        int endIndex = 0;
        if (value.charAt(0) == '[' || value.charAt(0) == '{') {

            int openBrackets = 1;
            for (int i = 1; i < value.length() && openBrackets > 0; i++) {
                char c = value.charAt(i);
                if ((c == '{' || c == '[')) {
                    openBrackets++;
                } else if ((c == '}' || c == ']')) {
                    openBrackets--;
                }
                endIndex = i;
            }
        } else {

            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                if (c == ',' || c == '}' || c == ']') {
                    break;
                }
                endIndex = i;
            }
        }
        return endIndex + 1;
    }
    @Override
    public String getDescription() {
        return "Shows all the matches.";
    }
}
