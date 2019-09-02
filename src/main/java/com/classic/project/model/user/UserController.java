package com.classic.project.model.user;

import com.classic.project.model.user.response.AddUserToCP;
import com.classic.project.model.user.response.ResponseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
    public ResponseEntity<List<ResponseUser>> addUsersToCp(@RequestBody AddUserToCP userIds) {
        return userService.addUsersToCp(userIds);
    }

    @RequestMapping(value = "/addUserToCp", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void addUserToCp(@RequestParam(name = "characterId") int characterId, @RequestParam(name = "cpId") int cpId) {
        userService.addUserToCp(characterId, cpId);
    }

    @RequestMapping(value = "/role/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TypeOfUser> getTypeOfUser(@PathVariable(name = "userId")int userId) {
	return userService.getTypeOfUser(userId);
    }

    @RequestMapping(value = "/updateRole", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserRole(@RequestParam(name = "characterId") int characterId, @RequestParam(name = "typeOfUser") String typeOfUser) {
	userService.updateUserRole(characterId, typeOfUser);
    }

    @RequestMapping(value = "/getUsersForDashboard", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseUser>> getUsersForDashboard() {
	return userService.getUsersForDashboard();
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam(name = "userId") int userId) {
        userService.deleteUser(userId);
    }


    @RequestMapping(value = "/verification", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Boolean> verifyUser(@RequestParam(name = "email") String email, @RequestParam(name = "mainChar")String mainChar) {
        return userService.verifyUser(email, mainChar);
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Boolean> updatePassword(@RequestBody String[] params) {
        return userService.updatePassword(params);
    }

    @RequestMapping(value = "/isCpMember", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Boolean> isCpMember(@RequestParam(name = "userId")int userId) {
        return userService.isCpMember(userId);
    }
}
