package com.classic.project.model.user;

import com.classic.project.model.character.Character;
import com.classic.project.model.character.CharacterRepository;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.character.exception.CharacterNotFoundException;
import com.classic.project.model.constantParty.ConstantParty;
import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.constantParty.file.GoogleCredential;
import com.classic.project.model.user.exception.UserExistException;
import com.classic.project.model.user.exception.UserNotFoundException;
import com.classic.project.model.user.response.AddUserToCP;
import com.classic.project.model.user.response.ResponseUser;
import com.classic.project.security.UserAuthConfirm;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
@CacheConfig(cacheNames={"membersDashBoard", "clanMembers"})
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

    private static final String APPLICATION_NAME = "Classic Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Value("${clan.total.spread.id}")
    private String spreadSheetId;

    @Override
    @CacheEvict(allEntries = true)
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
    @CacheEvict(allEntries = true)
    public void addUsersToCp(AddUserToCP userIds) {
        Optional<ConstantParty> cpFromDb = constantPartyRepository.findById(userIds.getCpId());
        int activePlayers = cpFromDb.get().getNumberOfActivePlayers() + userIds.getUsersToUpdate().length;
        int numberOfBoxes = cpFromDb.get().getNumberOfBoxes();
        for (int i = 0; i < userIds.getUsersToUpdate().length; i++) {
            Optional<User> userFromDb = userRepository.findById(userIds.getUsersToUpdate()[i]);
            numberOfBoxes += userFromDb.get().getCharacters().stream().filter(character -> character.getTypeOfCharacter().name().equals(
                    TypeOfCharacter.BOX.name())).count();
            userRepository.addUsersToCP(userIds.getCpId(), userIds.getUsersToUpdate()[i]);
        }
        constantPartyRepository.addUsersTpCP(activePlayers, numberOfBoxes, userIds.getCpId());
    }

    @Override
    public ResponseEntity<TypeOfUser> getTypeOfUser(int userId) {
        return new ResponseEntity<>(userRepository.getTypeOfUser(userId), HttpStatus.OK);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void updateUserRole(int characterId, String typeOfUser) {
        int userId = userRepository.getUserByCharacterId(characterId);
        userRepository.updateUserRole(userId, TypeOfUser.values()[Integer.parseInt(typeOfUser)]);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void addUserToCp(int characterId, int cpId) {
        Optional<Character> charFromDb = characterRepository.findById(characterId);
        if(!charFromDb.isPresent()){
            throw new CharacterNotFoundException(characterId);
        }
        int userId = charFromDb.get().getUser().getUserId();
        userRepository.addUsersToCP(cpId, userId);
        Optional<ConstantParty> cpFromDb = constantPartyRepository.findById(cpId);
        int activePlayers = cpFromDb.get().getNumberOfActivePlayers() + 1;
        int numberOfBoxes = cpFromDb.get().getNumberOfBoxes();
        Optional<User> userFromDb = userRepository.findById(userId);
        numberOfBoxes += userFromDb.get().getCharacters().stream().filter(character -> character.getTypeOfCharacter().name().equals(
                TypeOfCharacter.BOX.name())).count();
        constantPartyRepository.addUsersTpCP(activePlayers, numberOfBoxes, cpId);
    }

    @Override
    @Cacheable(value = "membersDashBoard")
    public ResponseEntity<List<ResponseUser>> getUsersForDashboard() {
        List<User> usersFromDb = userRepository.findAll();
        List<ResponseUser> response = new ArrayList<>();
        usersFromDb.forEach(user -> response.add(ResponseUser.convertForDashboard(user)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteUser(int userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        if(!userFromDb.isPresent()){
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteUserByUserId(userId);
    }

    @Override
    public ResponseEntity<List<List<Object>>> getEpicPointsPrice() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCredential.getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        final String range = "System Inflation Adj!A28:B29";
        ValueRange response = service.spreadsheets().values()
                .get(spreadSheetId, range)
                .execute();
        return new ResponseEntity<>(response.getValues(), HttpStatus.OK);
    }
}
