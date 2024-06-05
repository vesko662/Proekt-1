package main.enums;

public enum CommandMessages {
    INVALID_PATH("Path does not exist!"),
    INVALID_JSON(""),
    INVALID_KEY("Key not found!"),
    INVALID_ARGUMENTS("Provided arguments are wrong or missing!"),
    ELEMENT_ALREADY_EXISTS("Element ath the given key already exist!");

    private  String message;

    CommandMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
