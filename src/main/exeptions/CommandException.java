package main.exeptions;

import main.enums.CommandMessages;

public class CommandException extends Exception {
    public CommandException(CommandMessages commandMessages) {
        super(commandMessages.getMessage());
    }
}