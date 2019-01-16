package com.classic.project.model.character.responce;

import com.classic.project.model.character.Character;

import java.util.ArrayList;
import java.util.List;

public class ResponseCharacter {

    private String name;
    private int level;
    private String cpName;
    private String classOfCharacter;
    private String clanName;
    private String typeOfCharacter;
    private String typeOfUser;

    public ResponseCharacter(String name, int level, String cpName, String classOfCharacter, String clanName,String typeOfCharacter, String typeOfUser) {
        this.name = name;
        this.level = level;
        this.cpName = cpName;
        this.classOfCharacter = classOfCharacter;
        this.clanName = clanName;
        this.typeOfCharacter = typeOfCharacter;
        this.typeOfUser = typeOfUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getClassOfCharacter() {
        return classOfCharacter;
    }

    public void setClassOfCharacter(String classOfCharacter) {
        this.classOfCharacter = classOfCharacter;
    }

    public String getClanName() {
        return clanName;
    }

    public void setClanName(String clanName) {
        this.clanName = clanName;
    }

    public String getTypeOfCharacter() {
	return typeOfCharacter;
    }

    public void setTypeOfCharacter(String typeOfCharacter) {
	this.typeOfCharacter = typeOfCharacter;
    }

    public String getTypeOfUser() {
	return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
	this.typeOfUser = typeOfUser;
    }

    public static List<ResponseCharacter> convert(List<Character> all) {
        List<ResponseCharacter> response = new ArrayList<>();
        all.forEach(member -> response.add(new ResponseCharacter(member.getInGameName(), member.getLevel(), member.getUser().getCp().getCpName(), member.getClassOfCharacter().getName(), member.getClan().getName(), member.getTypeOfCharacter().name(), member.getUser().getTypeOfUser().name())));
        return response;
    }
}
