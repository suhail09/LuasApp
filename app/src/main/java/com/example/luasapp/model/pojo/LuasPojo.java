package com.example.luasapp.model.pojo;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.time.OffsetDateTime;
import java.util.List;

@Root(name="stopInfo")
public class LuasPojo {

    @Element
    private String message;
    @ElementList(name="direction", inline = true)
    private List<Direction> direction;

    @Attribute(name = "created")
    private String created;
    @Attribute(name = "stop")
    private String stop;
    @Attribute(name = "stopAbv")
    private String stopAbv;

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public List<Direction> getDirection() { return direction; }
    public void setDirection(List<Direction> value) { this.direction = value; }

    public String getCreated() { return created; }
    public void setCreated(String value) { this.created = value; }

    public String getStop() { return stop; }
    public void setStop(String value) { this.stop = value; }

    public String getStopAbv() { return stopAbv; }
    public void setStopAbv(String value) { this.stopAbv = value; }
}

