package main.exeptions;

import main.enums.ValidationMessages;

public class ValidateException extends Exception {
    public ValidateException(ValidationMessages validationMessages,int position) {
        super("Error at position "+position+": "+validationMessages.getMessage());
    }
}