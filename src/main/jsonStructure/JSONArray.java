package main.jsonStructure;

import main.contracts.JSON;

import java.util.*;

public class JSONArray implements JSON {
    private  List<JSON> list;

    public JSONArray() {
        this.list = new ArrayList<>();
    }

    public void add(JSON value) {
        list.add(value);
    }

    public JSON get(int index) {
        return list.get(index);
    }

    public void set(int index, JSON value) {
        list.set(index, value);
    }

    public JSON remove(int index) {
        return list.remove(index);
    }

    public int size() {
        return list.size();
    }

    @Override
    public String stringify(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = " ".repeat(indentLevel * 4);
        sb.append("[\n");
        int count = 0;
        for (JSON value : list) {
            if (count > 0) {
                sb.append(",\n");
            }
            sb.append(indent).append("    ").append(value.stringify(indentLevel + 1));
            count++;
        }
        sb.append("\n").append(indent).append("]");
        return sb.toString();
    }
}