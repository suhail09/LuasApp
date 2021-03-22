package com.example.luasapp.model.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "tram")
public class Tram {

    @Attribute(name = "dueMins")
    private String dueMins;

    @Attribute(name = "destination")
    private String destination;

    public String getDueMins() { return dueMins; }
    public void setDueMins(String value) { this.dueMins = value; }

    public String getDestination() { return destination; }
    public void setDestination(String value) { this.destination = value; }
}