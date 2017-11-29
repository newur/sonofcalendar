package com.sonofbeach.sonofcalendar.model.photos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PhotoAlbum {

    @Id
    @GeneratedValue
    private long id;

    private String type;
    private String url;
    private String name;

    public PhotoAlbum(String type, String name, String url) {
        this.type = type;
        this.name = name;
        this.url = url;
    }

    public PhotoAlbum(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PhotoAlbum{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
