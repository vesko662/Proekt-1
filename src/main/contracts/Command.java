package main.contracts;

import main.exceptions.CommandException;

public interface Command {
    void execute(String args) throws CommandException;
    String getDescription();
}
