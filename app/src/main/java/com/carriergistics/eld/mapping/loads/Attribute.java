package com.carriergistics.eld.mapping.loads;

public class Attribute {
    
    int id;
    boolean isPrimary;
    String name;
    String value;

    public Attribute(int id, boolean isPrimary, String name, String value) {
        this.id = id;
        this.isPrimary = isPrimary;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
