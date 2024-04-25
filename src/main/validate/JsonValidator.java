package main.validate;

import main.contracts.Validate;

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

        private void error(String message) throws Exception {
            throw new Exception("Error at position " + position + ": " + message);
        }

        private void validateObject() throws Exception {
            if (currentChar() != '{') error("Expected '{'");
            nextChar();
            skipWhitespace();

            if (currentChar() == '}') {
                nextChar();
                return;
            }

            while (true) {
                validateString();
                skipWhitespace();
                if (currentChar() != ':') error("Expected ':' after key");
                nextChar();
                skipWhitespace();
                validateValue();
                skipWhitespace();

                if (currentChar() == '}') {
                    nextChar();
                    return;
                }

                if (currentChar() != ',') error("Expected ',' or '}'");
                nextChar();
                skipWhitespace();
            }
        }

        private void validateArray() throws Exception {
            if (currentChar() != '[') error("Expected '['");
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

                if (currentChar() != ',') error("Expected ',' or ']'");
                nextChar();
                skipWhitespace();
            }
        }

        private void validateString() throws Exception {
            if (currentChar() != '\"') error("Expected '\"'");
            do {
                nextChar();
                if (currentChar() == '\\') {
                    nextChar();
                    nextChar();
                }
            } while (hasNext() && currentChar() != '\"');

            if (currentChar() != '\"') error("Unclosed string");
            nextChar();
        }

        private void validateValue() throws Exception {
            skipWhitespace();
            char c = currentChar();
            if (c == '{') {
                validateObject();
            } else if (c == '[') {
                validateArray();
            } else if (c == '\"') {
                validateString();
            } else if (Character.isDigit(c) || c == '-') {

                while (hasNext() && !Character.isWhitespace(currentChar()) &&
                        currentChar() != ',' && currentChar() != ']' && currentChar() != '}') {
                    nextChar();
                }
            } else {
                error("Invalid value");
            }
        }

        @Override
        public boolean validate() throws Exception {
            validateValue();
            return true;
        }
}


