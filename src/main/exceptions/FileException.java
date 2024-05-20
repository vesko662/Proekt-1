package main.exceptions;

import main.enums.FileMessages;

public class FileException extends Exception {


    public FileException(FileMessages fileMessages) {
        super(fileMessages.getMessage());
    }
}
