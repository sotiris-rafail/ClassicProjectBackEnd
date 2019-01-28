package com.classic.project.model.character.responce;

import com.classic.project.model.character.ClassOfCharacter;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.character.Character;

public class RegisterCharacter {

    private String inGameName;
    private int level;
    private ClassOfCharacter classOfCharacter;
    private TypeOfCharacter typeOfCharacter;
    private int userId;
    private int clanId;


    public String getInGameName() {
	return inGameName;
    }

    public int getLevel() {
	return level;
    }

    public ClassOfCharacter getClassOfCharacter() {
	return classOfCharacter;
    }

    public TypeOfCharacter getTypeOfCharacter() {
	return typeOfCharacter;
    }

    public int getUserId() {
	return userId;
    }

    public int getClanId() {
	return clanId;
    }

    public static Character convertToDBObject(RegisterCharacter registerCharacter){
        return new Character(registerCharacter.getInGameName(), registerCharacter.getLevel(), registerCharacter.getClassOfCharacter(), registerCharacter.getTypeOfCharacter());
    }
}
