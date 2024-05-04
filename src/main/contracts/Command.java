package main.contracts;

import main.exeptions.CommandException;

public interface Command {
    void execute(String args) throws CommandException;
    String getDescription();
}
