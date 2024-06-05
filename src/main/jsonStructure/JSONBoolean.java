package main.jsonStructure;

import main.contracts.JSON;

public class JSONBoolean implements JSON {
    private  Boolean value;

    public JSONBoolean(Boolean value) {
        this.value = value;
    }

    @Override
    public String stringify(int indentLevel) {
        return value.toString();
    }
}