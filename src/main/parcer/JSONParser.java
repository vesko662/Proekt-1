package main.parcer;

import main.contracts.JSON;
import main.enums.ValidationMessages;
import main.exceptions.ValidateException;
import main.jsonStructure.*;

public class JSONParser {
    private int index;
    private String json;

    public JSON parse(String json) throws ValidateException {
        this.json = json;
        this.index = 0;
        return parseValue();
    }

    private JSON parseValue() throws ValidateException {
        skipWhitespace();
        if (index >= json.length()) {
            error(ValidationMessages.INVALID_JSON_END);
        }
        char c = json.charAt(index);
        switch (c) {
            case '{':
                return parseObject();
            case '[':
                return parseArray();
            case '"':
                return parseString();
            case 't':
            case 'f':
                return parseBoolean();
            case 'n':
                return parseNull();
            default:
                if ((c >= '0' && c <= '9') || c == '-') {
                    return parseNumber();
                }
                error(ValidationMessages.INVALID_VALUE);
        }
        return null;
    }

    private void skipWhitespace() {
        while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
            index++;
        }
    }

    private JSONObject parseObject() throws ValidateException {
        JSONObject obj = new JSONObject();
        index++;
        skipWhitespace();
        if (json.charAt(index) == '}') {
            index++;
            return obj;
        }
        while (true) {
            skipWhitespace();
            if (json.charAt(index) != '"') {
                error(ValidationMessages.EXPECTED_QUOTES);
            }
            JSONString key = parseString();
            skipWhitespace();
            if (json.charAt(index) != ':') {
                error(ValidationMessages.EXPECTED_DUALITY);
            }
            index++;
            JSON value = parseValue();
            obj.put(key.stringify().replace("\"", ""), value);
            skipWhitespace();
            if (json.charAt(index) == ',') {
                index++;
                skipWhitespace();
            } else if (json.charAt(index) == '}') {
                index++;
                break;
            } else {
               error(ValidationMessages.EXPECTED_CURLY_BRACKETS_OR_COMMA);
            }
        }
        return obj;
    }

    private JSONArray parseArray() throws ValidateException {
        JSONArray array = new JSONArray();
        index++;
        skipWhitespace();
        if (json.charAt(index) == ']') {
            index++;
            return array;
        }
        while (true) {
            array.add(parseValue());
            skipWhitespace();
            if (json.charAt(index) == ',') {
                index++;
                skipWhitespace();
            } else if (json.charAt(index) == ']') {
                index++;
                break;
            } else {
                error(ValidationMessages.EXPECTED_SQUARE_BRACKETS_OR_COMMA);
            }
        }
        return array;
    }

    private JSONString parseString() throws ValidateException {
        index++;
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (index >= json.length()) {
                error(ValidationMessages.UNCLOSED_STRING);
            }
            char c = json.charAt(index);
            if (c == '"') {
                index++;
                break;
            }
            if (c == '\\') {
                index++;
                if (index >= json.length()) {
                    error(ValidationMessages.UNCLOSED_STRING);
                }
                c = json.charAt(index);
                switch (c) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    case 't': sb.append('\t'); break;
                    default: error(ValidationMessages.INVALID_VALUE);
                }
            } else {
                sb.append(c);
            }
            index++;
        }
        return new JSONString(sb.toString());
    }

    private JSONNumber parseNumber() {
        int start = index;
        while (index < json.length() && (Character.isDigit(json.charAt(index)) || json.charAt(index) == '.' || json.charAt(index) == '-' || json.charAt(index) == 'e' || json.charAt(index) == 'E' || json.charAt(index) == '+')) {
            index++;
        }
        return new JSONNumber(Double.parseDouble(json.substring(start, index)));
    }

    private JSONBoolean parseBoolean() throws ValidateException {
        if (json.startsWith("true", index)) {
            index += 4;
            return new JSONBoolean(true);
        } else if (json.startsWith("false", index)) {
            index += 5;
            return new JSONBoolean(false);
        }
        error(ValidationMessages.INVALID_BOOLEAN);
        return null;
    }

    private JSONNull parseNull() throws ValidateException {
        if (json.startsWith("null", index)) {
            index += 4;
            return new JSONNull();
        }
        error(ValidationMessages.INVALID_VALUE);
        return null;
    }

    private void error(ValidationMessages validationMessages) throws ValidateException {
        throw new ValidateException(validationMessages,index);
    }
}
