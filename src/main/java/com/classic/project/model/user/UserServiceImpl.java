package com.classic.project.model.user;

import com.classic.project.model.character.Character;
import com.classic.project.model.character.CharacterRepository;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.character.exception.CharacterNotFoundException;
import com.classic.project.model.constantParty.ConstantParty;
import com.classic.project.model.constantParty.ConstantPartyRepository;
import com.classic.project.model.user.exception.UserExistException;
import com.classic.project.model.user.exception.UserNotFoundException;
import com.classic.project.model.user.option.Option;
import com.classic.project.model.user.option.OptionRepository;
import com.classic.project.model.user.response.AddUserToCP;
import com.classic.project.model.user.response.ResponseUser;
import com.classic.project.model.user.verification.*;
import com.classic.project.security.UserAuthConfirm;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Component
@CacheConfig(cacheNames = {"membersDashBoard", "clanMembers"})
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

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private VerificationRepository verificationRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private static String verificationURL;

    @Value("${verification.url.email}")
    private void setVerificationURl(String url) {
        UserServiceImpl.verificationURL = url;
    }

    private static final String APPLICATION_NAME = "Classic Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Value("${clan.total.spread.id}")
    private String spreadSheetId;

    @Override
    @CacheEvict(allEntries = true)
    public ResponseEntity<String> registerUser(User user) {
        User userFromDb = userRepository.findUserByEmailLowerCase(user.getEmail().toLowerCase());
        if (userFromDb == null) {
            user.setEmailLowerCase(user.getEmail().toLowerCase());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userFromDb = userRepository.save(user);
            saveOptionsOnRegister(userFromDb);
            saveVerification(userFromDb);
        } else {
            throw new UserExistException(user.getEmail());
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(User.class).slash("user").slash(userFromDb.getUserId()).toUri());
        return new ResponseEntity<>(httpHeaders.getLocation().getPath(), HttpStatus.CREATED);
    }

    private void saveOptionsOnRegister(User user) {
        Option option = new Option();
        option.setUserOption(user);
        optionRepository.save(option);
    }

    private void saveVerification(User user) {
        Verification verification = new Verification();
        verification.setId(user.getUserId());
        verification.setUserVerification(user);
        verification.setStatus(VerificationStatus.ZERO);
        verificationRepository.save(verification);
    }

    @Override
    public ResponseEntity<ResponseUser> getUserById(int userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        userAuthConfirm.isTheAuthUser(userFromDb.get());
        ResponseUser responseUser = ResponseUser.convertForUser(userFromDb);
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseUser>> getCharsWithoutCp() {
        List<ResponseUser> responseUser = ResponseUser.convertForUsersWithoutCp(userRepository.findUsersWithoutCP());
        return new ResponseEntity<>(responseUser, HttpStatus.OK);
    }

    @Override
    @CacheEvict(allEntries = true)
    public ResponseEntity<List<ResponseUser>> addUsersToCp(AddUserToCP userIds) {
        List<User> responseUsers = new ArrayList<>();
        Optional<ConstantParty> cpFromDb = constantPartyRepository.findById(userIds.getCpId());
        int activePlayers = cpFromDb.get().getNumberOfActivePlayers() + userIds.getUsersToUpdate().length;
        int numberOfBoxes = cpFromDb.get().getNumberOfBoxes();
        for (int i = 0; i < userIds.getUsersToUpdate().length; i++) {
            Optional<User> userFromDb = userRepository.findById(userIds.getUsersToUpdate()[i]);
            numberOfBoxes += userFromDb.get().getCharacters().stream().filter(character -> character.getTypeOfCharacter().name().equals(
                    TypeOfCharacter.BOX.name())).count();
            userRepository.addUsersToCP(userIds.getCpId(), userIds.getUsersToUpdate()[i]);
        }
        for (int i = 0; i < userIds.getUsersToUpdate().length; i++) {
            responseUsers.add(userRepository.findById(userIds.getUsersToUpdate()[i]).get());
        }
        constantPartyRepository.addUsersTpCP(activePlayers, numberOfBoxes, userIds.getCpId());
        return new ResponseEntity<>(ResponseUser.convertForCp(responseUsers), HttpStatus.OK);
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
        if (!charFromDb.isPresent()) {
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
        if (!userFromDb.isPresent()) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteUserByUserId(userId);
    }

    @Override
    public ResponseEntity<Boolean> verifyUser(String email, String mainChar) {
        User userFromDb = userRepository.findUserByEmail(email);
        if (userFromDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (userFromDb.getCharacters().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for (Character chars : userFromDb.getCharacters()) {
            if (chars.getInGameName().equals(mainChar)) {
                return new ResponseEntity<>(true, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Boolean> updatePassword(String[] params) {
        User userFromDb = userRepository.findUserByEmail(params[0]);
        if (userFromDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userFromDb.setPassword(passwordEncoder.encode(params[1]));
        userRepository.save(userFromDb);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> isCpMember(int userId) {
        return new ResponseEntity<>(userRepository.isCpMember(userId).isPresent(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VerificationStatus> sendVerificationEmailToUser(String email) {
        User userFromDb = userRepository.findUserByEmail(email);
        if(!userRepository.isCpMember(userFromDb.getUserId()).isPresent()) {
            throw new UserNotFoundException("You are not a CP member.", 0);
        }
        if (userFromDb == null) {
            throw new UserNotFoundException(email);
        } else {
            if (userFromDb.getVerification() != null) {
                if (userFromDb.getVerification().getExpirationDate() != null && !userFromDb.getVerification().getExpirationDate().after(new Date())) {
                    throw new VerificationEmailException("Your verification has expired");
                } else if (!userFromDb.getVerification().getStatus().equals(VerificationStatus.ZERO)) {
                    throw new VerificationEmailException(getExceptionMessage(userFromDb.getVerification().getStatus()));
                }
            }
        }
        userFromDb.getVerification().setCode(VerificationServiceImpl.generateCode(userFromDb.getEmail(), userFromDb.getRegistrationDate()));
        userFromDb.getVerification().setRegistrationDate(new Date());
        userFromDb.getVerification().setExpirationDate(getExpirationVerificationDate());
        userFromDb.getVerification().setStatus(VerificationStatus.PENDING);
        Verification verification = verificationRepository.save(userFromDb.getVerification());
        return new ResponseEntity<>(sendVerificationMail(verification, userFromDb.getEmail()), HttpStatus.OK);
    }

    private static Date getExpirationVerificationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    private static String getExceptionMessage(VerificationStatus status) {
        if (status.equals(VerificationStatus.VERIFIED)) {
            return "Your email has been verified";
        } else {
            return "You have a pending verification";
        }
    }

    private VerificationStatus sendVerificationMail(Verification verification, String email) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(email);
        mail.setText(verificationMessage(verification));
        mail.setSubject("Email Verification");
        mail.setFrom("inquisitionAlliance@gmail.com");
        mail.setSentDate(new Date());
        try {
            javaMailSender.send(mail);
            return VerificationStatus.PENDING;
        }catch (Exception e) {
            verification.setExpirationDate(null);
            verification.setStatus(VerificationStatus.ZERO);
            verification.setCode(null);
            verificationRepository.save(verification);
            System.out.println(e.getMessage());
        }
        return VerificationStatus.ZERO;
    }

    private static String verificationMessage(Verification verification) {
        return verificationURL + verification.getCode() +" \n" +
                "Your code expires in " +verification.getExpirationDate();
    }

    @Override
    public ResponseEntity<VerificationStatus> acceptVerificationMailCode(String code) {
        Verification verification = verificationRepository.findByCode(code);
        if(verification != null) {
            if (verification.getStatus().equals(VerificationStatus.VERIFIED)) {
                return new ResponseEntity<>(VerificationStatus.ALREADY_VERIFIED, HttpStatus.OK);
            }
            Optional<User> user = userRepository.findById(verification.getUserVerification().getUserId());
            if (!user.isPresent()) {
                throw new UserNotFoundException();
            }
            Verification ver;
            if (verifyCodeEquality(code, user.get()) && user.get().getVerification().getExpirationDate().after(new Date())) {
                verification.setStatus(VerificationStatus.VERIFIED);
                verification.setExpirationDate(null);
                ver = verificationRepository.save(verification);
                return new ResponseEntity<>(ver.getStatus(), HttpStatus.OK);
            } else {
                throw new VerificationEmailException("You have no pending requests or your code has expired.");
            }
        } else {
            throw new VerificationEmailException("Your code does not exist");
        }
    }

    private static boolean verifyCodeEquality(String code, User user) {
        return code.equals(VerificationServiceImpl.generateCode(user.getEmail(), user.getRegistrationDate()));
    }

}