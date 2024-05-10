package main.enums;

public enum FileMessages {

    WRONG_EXTENSION("The file must be .json!"),
    UNSUCCESSFUL_CREATED_FILE("Error occur when creating file!"),
    ERROR_WHEN_READING_FILE("Error occur when reading file!"),
    INCORRECT_VALIDATION(""),
    FILE_OPEN("File is already open!");

    private  String message;

    FileMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
