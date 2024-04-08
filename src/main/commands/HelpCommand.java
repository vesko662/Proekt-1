package main.commands;

import main.contracts.Command;

import java.util.Map;

public class HelpCommand implements Command {
    private Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public void execute(String args) {
        System.out.println("Available commands:");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getDescription());
        }
    }

    @Override
    public String getDescription() {
        return "Displays the list of available commands and their descriptions.";
    }
}
