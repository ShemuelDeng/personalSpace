package com.shemuel.site.repository;

import com.shemuel.site.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserRepository {

    User save(User user);
    Optional<User> findByEmail(String email);
    void update(User user);
    void delete(String userId);

}
