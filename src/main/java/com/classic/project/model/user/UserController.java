package com.classic.project.model.user;

import com.classic.project.model.user.response.AddUserToCP;
import com.classic.project.model.user.response.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> registerUser(@RequestBody User user) {
	return userService.registerUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseUser> getUserById(@PathVariable(name = "userId") int userId) {
	return userService.getUserById(userId);
    }

    @RequestMapping(value = "/noCpPeople", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseUser>> getCharsWithoutCp() {
        return userService.getCharsWithoutCp();
    }

    @RequestMapping(value = "/addPeopleToCp", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void addUserToCp(@RequestBody AddUserToCP userIds) {
        userService.addUserToCp(userIds);
    }

    @RequestMapping(value = "/role/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TypeOfUser> getTypeOfUser(@PathVariable(name = "userId")int userId) {
	return userService.getTypeOfUser(userId);
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void addUserToCp(@RequestParam(name = "characterId") int characterId, @RequestParam(name = "typeOfUser") String typeOfUser) {
	userService.updateUserRole(characterId, typeOfUser);
    }
}
