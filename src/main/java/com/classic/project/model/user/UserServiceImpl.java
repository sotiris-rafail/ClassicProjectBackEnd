package com.classic.project.model.user;

import com.classic.project.model.user.response.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<HttpHeaders> registerUser(User user) {
        User userFromDb = userRepository.findUserByEmail(user.getEmail());
        if(userFromDb == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            throw new UserExistException(user.getEmail());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(User.class).slash(userFromDb.getUserId()).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseUser> getUserById(int userId) {
        ResponseUser userFromDb = ResponseUser.convert(userRepository.findById(userId));
        return new ResponseEntity<>(userFromDb, HttpStatus.OK);
    }
}
