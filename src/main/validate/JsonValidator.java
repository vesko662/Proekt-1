package main.validate;

import main.contracts.Validate;
import main.enums.ValidationMessages;
import main.exeptions.ValidateException;

public class JsonValidator implements Validate {

        private String json;
        private int position = 0;
        private int length;

        public JsonValidator(String json) {
            this.json = json.trim();
            this.length = this.json.length();
        }

        private char currentChar() {
            return json.charAt(position);
        }

        private char nextChar() {
            if (position < length - 1) {
                position++;
            }
            return json.charAt(position);
        }

        private boolean hasNext() {
            return position < length;
        }

        private void skipWhitespace() {
            while (hasNext() && Character.isWhitespace(currentChar())) {
                position++;
            }
        }

        private void error(ValidationMessages validationMessages) throws ValidateException {
            throw new ValidateException(validationMessages,position);
        }

        private void validateObject() throws ValidateException {
            if (currentChar() != '{') error(ValidationMessages.EXPECTED_CURLY_BRACKETS1);
            nextChar();
            skipWhitespace();

            if (currentChar() == '}') {
                nextChar();
                return;
            }

            while (true) {
                validateString();
                skipWhitespace();
                if (currentChar() != ':') error(ValidationMessages.EXPECTED_DUALITY);
                nextChar();
                skipWhitespace();
                validateValue();
                skipWhitespace();

                if (currentChar() == '}') {
                    nextChar();
                    return;
                }

                if (currentChar() != ',') error(ValidationMessages.EXPECTED_CURLY_BRACKETS_OR_COMMA);
                nextChar();
                skipWhitespace();
            }
        }

        private void validateArray() throws ValidateException {
            if (currentChar() != '[') error(ValidationMessages.EXPECTED_SQUARE_BRACKETS1);
            nextChar();
            skipWhitespace();

            if (currentChar() == ']') {
                nextChar();
                return;
            }

            while (true) {
                validateValue();
                skipWhitespace();

                if (currentChar() == ']') {
                    nextChar();
                    return;
                }

                if (currentChar() != ',') error(ValidationMessages.EXPECTED_SQUARE_BRACKETS_OR_COMMA);
                nextChar();
                skipWhitespace();
            }
        }

        private void validateString() throws ValidateException {
            if (currentChar() != '\"') error(ValidationMessages.EXPECTED_QUOTES);
            do {
                nextChar();
                if (currentChar() == '\\') {
                    nextChar();
                    nextChar();
                }
            } while (hasNext() && currentChar() != '\"');

            if (currentChar() != '\"') error(ValidationMessages.UNCLOSED_STRING);
            nextChar();
        }

        public void validateValue() throws ValidateException {
            skipWhitespace();
            char c = currentChar();
            if (c == '{') {
                validateObject();
            } else if (c == '[') {
                validateArray();
            } else if (c == '\"') {
                validateString();
            }else if (c == 't' || c == 'f') {
                validateBoolean();
            }else if (Character.isDigit(c) || c == '-') {

                while (hasNext() && !Character.isWhitespace(currentChar()) &&
                        currentChar() != ',' && currentChar() != ']' && currentChar() != '}') {
                    nextChar();
                }
            } else {
                error(ValidationMessages.INVALID_VALUE);
            }
        }

        private void validateBoolean() throws ValidateException {
        if (json.startsWith("true", position)) {
            position += 4;
        } else if (json.startsWith("false", position)) {
            position += 5;
        } else {
            error(ValidationMessages.INVALID_BOOLEAN);
        }
    }
        @Override
        public void validate() throws ValidateException {
            if (!json.startsWith("{")) {
                error(ValidationMessages.INVALID_JSON_START);
            }
            validateValue();
        }
}


