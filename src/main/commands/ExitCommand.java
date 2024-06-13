package main.commands;

import main.contracts.Command;
import main.contracts.JSON;
import main.exceptions.CommandException;

public class ExitCommand implements Command {
    @Override
    public JSON execute(String args, JSON j) throws CommandException {
        System.out.println("Exiting the program...");
        System.exit(0);
        return null;
    }

    @Override
    public String getDescription() {
        return "End the program.";
    }
}
