package main.contracts;

public interface JSON {
    String stringify(int indentLevel);
    default String stringify() {
        return stringify(0);
    }
}
