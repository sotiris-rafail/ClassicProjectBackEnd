package com.classic.project.model.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public ResponseEntity<HttpHeaders> registerUser(User user) {
        User userFromDb = userRepository.findUserByEmail(user.getEmail());
        if(userFromDb == null) {
            userRepository.save(user);
	} else {
            throw new UserExistException(user.getEmail());
	}
	HttpHeaders httpHeaders = new HttpHeaders();
	httpHeaders.setLocation(linkTo(User.class).slash(userFromDb.getUserId()).toUri());
	return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> getUserById(int userId) {
	Optional<User> userFromDb = userRepository.findById(userId);
	return userFromDb.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
	    .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
