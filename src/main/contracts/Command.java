package main.contracts;

import main.exceptions.CommandException;

public interface Command {
    JSON execute(String args, JSON j) throws CommandException;
    String getDescription();
}
