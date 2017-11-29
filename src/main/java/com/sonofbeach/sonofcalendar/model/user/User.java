package com.sonofbeach.sonofcalendar.model.user;

import com.sonofbeach.sonofcalendar.model.calendar.CalendarSettings;
import com.sonofbeach.sonofcalendar.model.clock.ClockSettings;
import com.sonofbeach.sonofcalendar.model.photos.PhotoSettings;
import com.sonofbeach.sonofcalendar.model.rss.RssSettings;
import com.sonofbeach.sonofcalendar.model.authorization.GoogleCredentials;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public User(){}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.photoSettings = new PhotoSettings();
        this.calendarSettings = new CalendarSettings();
        this.rssSettings = new RssSettings();
        this.clockSettings = new ClockSettings();
        this.googleCredentials = new GoogleCredentials();

        //Default settings
        this.getPhotoSettings().setPhotoTransitionTime(60);
        this.getCalendarSettings().setCalendarType("days");
        this.getCalendarSettings().setMaxDays(7);
        this.getRssSettings().setRssTransitionTime(30);
        this.getClockSettings().setClockType("digital");
    }

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String birthday;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private ClockSettings clockSettings;

    @OneToOne(cascade = CascadeType.ALL)
    private PhotoSettings photoSettings;

    @OneToOne(cascade = CascadeType.ALL)
    private CalendarSettings calendarSettings;

    @OneToOne(cascade = CascadeType.ALL)
    private RssSettings rssSettings;

    @OneToOne(cascade = CascadeType.ALL)
    private GoogleCredentials googleCredentials;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClockSettings getClockSettings() {
        return clockSettings;
    }

    public PhotoSettings getPhotoSettings() {
        return photoSettings;
    }

    public CalendarSettings getCalendarSettings() {
        return calendarSettings;
    }

    public RssSettings getRssSettings() {
        return rssSettings;
    }

    public GoogleCredentials getGoogleCredentials() {
        return googleCredentials;
    }

    public void setClockSettings(ClockSettings clockSettings) {
        this.clockSettings = clockSettings;
    }

    public void setPhotoSettings(PhotoSettings photoSettings) {
        this.photoSettings = photoSettings;
    }

    public void setCalendarSettings(CalendarSettings calendarSettings) {
        this.calendarSettings = calendarSettings;
    }

    public void setRssSettings(RssSettings rssSettings) {
        this.rssSettings = rssSettings;
    }

    public void setGoogleCredentials(GoogleCredentials googleCredentials) {
        this.googleCredentials = googleCredentials;
    }
}