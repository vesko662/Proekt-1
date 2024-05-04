package main.commands;

import main.contracts.Command;
import main.exeptions.CommandException;

public class ExitCommand implements Command {
    @Override
    public void execute(String args) throws CommandException {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "End the program.";
    }
}
