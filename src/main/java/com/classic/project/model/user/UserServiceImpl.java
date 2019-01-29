package com.classic.project.model.user;

import com.classic.project.model.user.response.ResponseUser;
import com.classic.project.security.UserAuthConfirm;
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
    private UserAuthConfirm userAuthConfirm;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> registerUser(User user) {
        User userFromDb = userRepository.findUserByEmail(user.getEmail());
        if(userFromDb == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userFromDb = userRepository.save(user);
        } else {
            throw new UserExistException(user.getEmail());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(User.class).slash("user").slash(userFromDb.getUserId()).toUri());
        return new ResponseEntity<>(httpHeaders.getLocation().getPath(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseUser> getUserById(int userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        userAuthConfirm.isTheAuthUser(userFromDb.get());
        ResponseUser responseUser =  ResponseUser.convertForUser(userFromDb);
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }
}
