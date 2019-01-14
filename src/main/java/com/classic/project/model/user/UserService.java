package com.classic.project.model.user;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<HttpHeaders> registerUser(User user);

    ResponseEntity<User> getUserById(int userId);
}
