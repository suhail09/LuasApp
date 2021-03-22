package com.example.luasapp.model.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "direction")
public class Direction {

    @ElementList(name="tram", inline = true)
    private List<Tram> tram;

    @Attribute(name = "name")
    private String name;

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public List<Tram> getTram() { return tram; }
    public void setTram(List<Tram> value) { this.tram = value; }
}