package com.classic.project.model.user;

import com.classic.project.model.user.response.ResponseUser;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<HttpHeaders> registerUser(User user);

    ResponseEntity<ResponseUser> getUserById(int userId);
}
