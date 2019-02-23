package com.classic.project.model.user;

import com.classic.project.model.character.CharacterRepository;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.constantParty.ConstantParty;
import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.user.response.AddUserToCP;
import com.classic.project.model.user.response.ResponseUser;
import com.classic.project.security.UserAuthConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserAuthConfirm userAuthConfirm;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CharacterRepository characterRepository;

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

    @Override
    public ResponseEntity<List<ResponseUser>> getCharsWithoutCp() {
        List<ResponseUser> responseUser = ResponseUser.convertForUsersWithoutCp(userRepository.findUsersWithoutCP());
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }

    @Override
    public void addUserToCp(AddUserToCP userIds) {
        Optional<ConstantParty> cpFromDb = constantPartyRepository.findById(userIds.getCpId());
	int activePlayers = cpFromDb.get().getNumberOfActivePlayers() + userIds.getUsersToUpdate().length;
	int numberOfBoxes = cpFromDb.get().getNumberOfBoxes();
	for (int i = 0; i < userIds.getUsersToUpdate().length; i++) {
	    Optional<User> userFromDb = userRepository.findById(userIds.getUsersToUpdate()[i]);
	    numberOfBoxes += userFromDb.get().getCharacters().stream().filter(character -> character.getTypeOfCharacter().name().equals(
		TypeOfCharacter.BOX)).count();
	    userRepository.addUsersToCP(userIds.getCpId(),userIds.getUsersToUpdate()[i]);
	}
	constantPartyRepository.addUsersTpCP(activePlayers, numberOfBoxes, userIds.getCpId());
    }

    @Override
    public ResponseEntity<TypeOfUser> getTypeOfUser(int userId) {
	return new ResponseEntity<>(userRepository.getTypeOfUser(userId), HttpStatus.OK);
    }

    @Override
    public void updateUserRole(int characterId, String typeOfUser) {
        int userId = userRepository.getUserByCharacterId(characterId);
	userRepository.updateUserRole(userId, TypeOfUser.values()[Integer.parseInt(typeOfUser)]);
    }
}
