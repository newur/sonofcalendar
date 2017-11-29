package com.sonofbeach.sonofcalendar.database;

import com.sonofbeach.sonofcalendar.model.user.User;

public interface UserProvider {

    User getUser();
    boolean setUser(User user);

}
