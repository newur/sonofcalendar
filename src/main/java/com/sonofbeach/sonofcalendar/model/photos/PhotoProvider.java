package com.sonofbeach.sonofcalendar.model.photos;


public enum PhotoProvider {

    GOOGLE("google");

    private String name;

    PhotoProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
