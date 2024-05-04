package main.commands;

import main.contracts.Command;
import main.singletons.FileData;

public class PrintCommand implements Command {

    @Override
    public void execute(String args) {
        FileData data = FileData.getInstance();
        String json = data.getFileData();
        StringBuilder result = new StringBuilder();


        int indentLevel = 0;
        boolean inQuotes = false;

        for (int i = 0; i < json.length(); i++) {
            char position = json.charAt(i);

            switch (position) {
                case '\"':
                    inQuotes = !inQuotes;
                    result.append(position);
                    break;
                case '{':
                case '[':
                    if (!inQuotes) {
                        result.append(position).append("\n");
                        indentLevel++;
                        appendIndent(result, indentLevel);
                    } else {
                        result.append(position);
                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes) {
                        result.append("\n");
                        indentLevel--;
                        appendIndent(result, indentLevel);
                        result.append(position);
                    } else {
                        result.append(position);
                    }
                    break;
                case ',':
                    if (!inQuotes) {
                        result.append(position).append("\n");
                        appendIndent(result, indentLevel);
                    } else {
                        result.append(position);
                    }
                    break;
                default:
                    if (!Character.isWhitespace(position) || inQuotes) {
                        result.append(position);
                    }
                    break;
            }
        }
        System.out.println(result.toString());
    }

    private  void appendIndent(StringBuilder result, int indentLevel) {
        result.append("    ".repeat(Math.max(0, indentLevel)));
    }

    @Override
    public String getDescription() {
        return "Print the json.";
    }
}
