package main.commands;

import main.contracts.Command;
import main.contracts.JSON;

import java.util.Map;

public class HelpCommand implements Command {
    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public JSON execute(String args, JSON j) {
        System.out.println("Available commands:");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }
        return null;
    }

    @Override
    public String getDescription() {
        return "Displays the list of available commands and their descriptions.";
    }
}
