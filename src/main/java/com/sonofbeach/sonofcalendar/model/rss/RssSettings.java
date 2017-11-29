package com.sonofbeach.sonofcalendar.model.rss;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RssSettings {

    @Id
    @GeneratedValue
    private long id;

    private String rssUrl;
    private int rssTransitionTime;

    public RssSettings(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public int getRssTransitionTime() {
        return rssTransitionTime;
    }

    public void setRssTransitionTime(int rssTransitionTime) {
        this.rssTransitionTime = rssTransitionTime;
    }

}
