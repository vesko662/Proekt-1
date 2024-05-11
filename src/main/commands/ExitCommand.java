package main.commands;

import main.contracts.Command;
import main.exeptions.CommandException;

public class ExitCommand implements Command {
    @Override
    public void execute(String args) throws CommandException {
        System.out.println("Exiting the program...");
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "End the program.";
    }
}
