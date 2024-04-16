package main.commands;

import main.contracts.Command;
import main.singletons.FileData;

public class PrintCommand implements Command {

    @Override
    public void execute(String args) {
        FileData data = FileData.getInstance();
        String json = data.getFileData();
        System.out.println(print(json));
    }

    public String print(String json) {
        StringBuilder printJson = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;
        int length = json.length();

        for (int i = 0; i < length; i++) {
            char charAt = json.charAt(i);
            switch (charAt) {
                case '\"':
                    inQuotes = !inQuotes;
                    printJson.append(charAt);
                    break;
                case '{':
                case '[':
                    if (!inQuotes) {
                        printJson.append(charAt);
                        printJson.append("\n").append(indent(indentLevel + 1));
                        indentLevel++;
                    } else {
                        printJson.append(charAt);
                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes) {
                        if (i > 0 && json.charAt(i - 1) != '{' && json.charAt(i - 1) != '[') {
                            printJson.append("\n").append(indent(indentLevel));
                        }
                        printJson.append(charAt);
                        indentLevel--;
                    } else {
                        printJson.append(charAt);
                    }
                    break;
                case ',':
                    printJson.append(charAt);
                    if (!inQuotes && nextCharNotBracket(json, i, length)) {
                        printJson.append("\n").append(indent(indentLevel));
                    }
                    break;
                default:
                    printJson.append(charAt);
            }
        }
        return printJson.toString();
    }

    private static String indent(int level) {
        return "    ".repeat(Math.max(0, level));
    }

    private static boolean nextCharNotBracket(String str, int index, int length) {
        for (int i = index + 1; i < length; i++) {
            char c = str.charAt(i);
            if (c == '}' || c == ']') return false;
            if (c != ' ' && c != '\n') return true;
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "Print the json.";
    }
}
