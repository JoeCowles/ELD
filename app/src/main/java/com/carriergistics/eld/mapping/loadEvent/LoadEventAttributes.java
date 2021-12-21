package com.carriergistics.eld.mapping.loadEvent;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root
public class LoadEventAttributes {

    @ElementList(inline = true)
    List<LoadEventAttribute> loadEventAttributes;

    public List<LoadEventAttribute> getAttributes(){
        return loadEventAttributes;
    }

}
