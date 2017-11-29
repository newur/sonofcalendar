package com.sonofbeach.sonofcalendar.model.calendar;

import javax.persistence.*;

@Entity
public class Calendar {

    @Id
    @GeneratedValue
    private long id;

    private String url;
    private String type;
    private String name;
    private String color;

    public Calendar() {
    }

    public Calendar(String type, String url, String name) {
        this.type = type;
        this.url = url;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
