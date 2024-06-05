package main.jsonStructure;

import main.contracts.JSON;

public class JSONNull  implements JSON {
    @Override
    public String stringify(int indentLevel) {
        return "null";
    }
}