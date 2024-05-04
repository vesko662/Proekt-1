package main.enums;

public enum ValidationMessages {
       EXPECTED_CURLY_BRACKETS1("Expected '{'!"),
    EXPECTED_DUALITY("Expected ':' after key!"),
    EXPECTED_CURLY_BRACKETS_OR_COMMA("Expected ',' or '}'!"),
    EXPECTED_SQUARE_BRACKETS1("Expected '['!"),
    EXPECTED_SQUARE_BRACKETS_OR_COMMA("Expected ',' or ']'!"),
    EXPECTED_QUOTES("Expected '\"'!"),
    UNCLOSED_STRING("Unclosed string!"),
    INVALID_VALUE("Invalid value!"),
    INVALID_BOOLEAN("Invalid boolean!"),
    INVALID_JSON_START("Json need to start with \"{\"!");

    private  String message;

    ValidationMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
