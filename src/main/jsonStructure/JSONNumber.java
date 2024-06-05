package main.jsonStructure;

import main.contracts.JSON;

public class JSONNumber implements JSON {
    private  Number value;

    public JSONNumber(Number value) {
        this.value = value;
    }

    @Override
    public String stringify(int indentLevel) {
        return value.toString();
    }
}