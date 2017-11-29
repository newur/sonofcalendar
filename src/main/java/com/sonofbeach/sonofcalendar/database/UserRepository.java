package com.sonofbeach.sonofcalendar.database;

import com.sonofbeach.sonofcalendar.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

//    void saveAccessToken(User user, String token);

}
