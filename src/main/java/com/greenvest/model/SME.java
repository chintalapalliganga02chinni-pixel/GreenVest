package com.greenvest.model;

public class SME {
    private final String id;
    private final String name;

    public SME(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
