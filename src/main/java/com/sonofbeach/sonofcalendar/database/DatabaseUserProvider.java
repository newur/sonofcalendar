package com.sonofbeach.sonofcalendar.database;

import com.sonofbeach.sonofcalendar.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class DatabaseUserProvider implements UserProvider {

    private UserRepository userRepository;

    @Autowired
    public DatabaseUserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser() {
        List<User> users = userRepository.findAll();
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public boolean setUser(User user) {
        userRepository.save(user);
        return true;
    }

}
