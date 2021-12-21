package com.carriergistics.eld.mapping.load;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="attribute")
public class Attribute {

    @Element
    int id;
    @Element
    int isPrimary;
    @Element
    String name;
    @Element
    String value;

    public Attribute(@Element(name="id") int id,
                     @Element(name="isPrimary") int isPrimary,
                     @Element(name="name") String name,
                     @Element(name="value") String value) {
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

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
