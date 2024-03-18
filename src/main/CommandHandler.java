package main;

import main.commands.*;
import main.contracts.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private  Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
        commands.put("open", new OpenCommand());
        commands.put("close", new CloseCommand());
        commands.put("save", new SaveCommand());
    }

    public void executeCommand(String input) {
        String[] sInput = input.split(" ");
        String commandKeyword = sInput[0];
        String[] args = new String[sInput.length - 1];
        System.arraycopy(sInput, 1, args, 0, sInput.length - 1);

        Command command = commands.get(commandKeyword);
        if (command != null) {
            command.execute(args);
        } else {
            System.out.println("Invalid command.");
        }
    }
}
