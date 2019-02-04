package com.classic.project.model.user.response;

import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.user.User;
import com.classic.project.model.user.UserNotFoundException;

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

    public static ResponseUser convertForUser(Optional<User> user){
        if(!user.isPresent()) {
            throw new UserNotFoundException();
        }
        return new ResponseUser(user.get().getUserId(), user.get().getEmail(), user.get().getTypeOfUser().name(), ResponseCharacter.convertForUser(user.get().getCharacters()), ResponseConstantParty.convertForUser(user.get().getCp()));
    }

    public static  List<ResponseUser> convertForCp(List<User> users){
        List<ResponseUser> members = new ArrayList<>();
        users.forEach(user -> members.add(new ResponseUser(user.getUserId(), user.getEmail(), user.getTypeOfUser().name(), ResponseCharacter.convertForCP(user.getCharacters()))));
        return members;
    }

    public static  List<ResponseUser> convertForUsersWithoutCp(List<User> users){
        users = users.stream().filter(user -> !user.getCharacters().isEmpty()).collect(Collectors.toList());
        List<ResponseUser> members = new ArrayList<>();
        users.forEach(user -> members.add(new ResponseUser(user.getUserId(), ResponseCharacter.convertForUsersWithoutCP(user.getCharacters()))));
        return members;
    }
}
