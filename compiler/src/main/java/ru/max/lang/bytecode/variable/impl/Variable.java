package ru.max.lang.bytecode.variable.impl;

public class Variable {
    private final int type;
    private final int id;
    private final String value;

    public Variable(int type, int id, String value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
