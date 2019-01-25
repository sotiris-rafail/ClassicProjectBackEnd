package com.classic.project.model.character.responce;

import com.classic.project.model.character.Character;
import com.classic.project.model.character.TypeOfCharacter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return returnNewList(all);
    }

    public static List<ResponseCharacter> convertForCP(List<Character> characters) {
        characters = characters.stream().filter(character -> character.getTypeOfCharacter().name().equals(TypeOfCharacter.MAIN.name())).collect(Collectors.toList());
        return returnNewList(characters);
    }

    private static List<ResponseCharacter> returnNewList(List<Character> characters){
        List<ResponseCharacter> cpMembers = new ArrayList<>();
        characters.forEach(member -> cpMembers.add(new ResponseCharacter(member.getInGameName(), member.getLevel(), member.getUser().getCp().getCpName(), member.getClassOfCharacter().getName(), member.getClan().getName(), member.getTypeOfCharacter().name(), member.getUser().getTypeOfUser().name())));
        return cpMembers;
    }
}