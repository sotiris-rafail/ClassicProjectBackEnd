package com.classic.project.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpHeaders> getStoresByLocation(@RequestBody User user) {
	return userService.registerUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> getStoresByLocation(@PathVariable(name = "userId") int userId) {
	return userService.getUserById(userId);
    }
}
