package main.jsonStructure;

import main.contracts.JSON;

public class JSONString implements JSON {
    private final String value;

    public JSONString(String value) {
        this.value = value;
    }

    @Override
    public String stringify(int indentLevel) {
        return "\"" + escapeString(value) + "\"";
    }

    private String escapeString(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }
}