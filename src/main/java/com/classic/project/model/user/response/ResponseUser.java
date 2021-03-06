package com.classic.project.model.user.response;

import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.user.User;
import com.classic.project.model.user.exception.UserNotFoundException;
import com.classic.project.model.user.option.response.ResponseOption;
import com.classic.project.model.user.verification.response.ResponseVerification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResponseUser {

    private int userId;
    private String email;
    private String typeOfUser;
    private List<ResponseCharacter> chars;
    private ResponseConstantParty responseConstantParty;
    private ResponseOption option;
    private ResponseVerification responseVerification;

    public ResponseUser(int userId, String email, String typeOfUser, List<ResponseCharacter> chars, ResponseConstantParty responseConstantParty,
                        ResponseOption responseOption, ResponseVerification responseVerification) {
        this.userId = userId;
        this.email = email;
        this.typeOfUser = typeOfUser;
        this.chars = chars;
        this.responseConstantParty = responseConstantParty;
        this.option = responseOption;
        this.responseVerification = responseVerification;
    }

    public ResponseUser(int userId, String email, String typeOfUser, List<ResponseCharacter> chars, ResponseConstantParty responseConstantParty, ResponseOption responseOption) {
        this.userId = userId;
        this.email = email;
        this.typeOfUser = typeOfUser;
        this.chars = chars;
        this.responseConstantParty = responseConstantParty;
        this.option = responseOption;
    }

    public ResponseUser(int userId, String email, String typeOfUser, List<ResponseCharacter> chars, ResponseConstantParty responseConstantParty) {
	this.userId = userId;
	this.email = email;
	this.typeOfUser = typeOfUser;
	this.chars = chars;
	this.responseConstantParty = responseConstantParty;
    }

    public ResponseUser(int userId, String email, String name, List<ResponseCharacter> convertForCP) {
        this.userId = userId;
        this.email = email;
        this.typeOfUser = name;
        this.chars = convertForCP;
    }

    public ResponseUser(int userId, List<ResponseCharacter> convertForCP) {
        this.userId = userId;
        this.chars = convertForCP;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public List<ResponseCharacter> getChars() {
        return chars;
    }

    public void setChars(List<ResponseCharacter> chars) {
        this.chars = chars;
    }

    public ResponseConstantParty getResponseConstantParty() {
        return responseConstantParty;
    }

    public void setResponseConstantParty(ResponseConstantParty responseConstantParty) {
        this.responseConstantParty = responseConstantParty;
    }

    public ResponseOption getOption() {
        return option;
    }

    public void setOption(ResponseOption option) {
        this.option = option;
    }

    public ResponseVerification getResponseVerification() {
        return responseVerification;
    }

    public void setResponseVerification(ResponseVerification responseVerification) {
        this.responseVerification = responseVerification;
    }

    public static ResponseUser convertForUser(Optional<User> user){
        if(!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return new ResponseUser(user.get().getUserId(), user.get().getEmail(),
	    user.get().getTypeOfUser().name(),
	    ResponseCharacter.convertForUser(user.get().getCharacters()),
	    ResponseConstantParty.convertForUser(user.get().getCp()),
	    ResponseOption.convertForUser(user.get().getOptions()),
        ResponseVerification.convertForUser(user.get().getVerification()));
    }

    public static List<ResponseUser> convertForCp(List<User> users){
        List<ResponseUser> members = new ArrayList<>();
        users.forEach(user -> members.add(new ResponseUser(user.getUserId(), user.getEmail(), user.getTypeOfUser().name(), ResponseCharacter.convertForCP(user.getCharacters()))));
        return members;
    }

    public static List<ResponseUser> convertForUsersWithoutCp(List<User> users){
        List<User> filterUsers = users.stream().filter(user -> !user.getCharacters().isEmpty()).collect(Collectors.toList());
        List<ResponseUser> members = new ArrayList<>();
        filterUsers.forEach(user -> members.add(new ResponseUser(user.getUserId(), ResponseCharacter.convertForUsersWithoutCP(user.getCharacters()))));
        return members;
    }

    public static ResponseUser convertForDashboard(User user){
        return new ResponseUser(user.getUserId(), user.getEmail(), user.getTypeOfUser().name(), ResponseCharacter.getForDashboard(user.getCharacters()), ResponseConstantParty.convertForUser(user.getCp()));
    }
}
