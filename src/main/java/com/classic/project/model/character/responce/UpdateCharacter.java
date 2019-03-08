package com.classic.project.model.character.responce;

public class UpdateCharacter {
    private int charId;
    private String inGameName;
    private int level;
    private int clanId;
    private int classOfCharacter;
    private int typeOfCharacter;

    public int getCharId() {
        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public String getInGameName() {
        return inGameName;
    }

    public void setInGameName(String inGameName) {
        this.inGameName = inGameName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getClanId() {
        return clanId;
    }

    public void setClanId(int clanId) {
        this.clanId = clanId;
    }

    public int getClassOfCharacter() {
        return classOfCharacter;
    }

    public void setClassOfCharacter(int classOfCharacter) {
        this.classOfCharacter = classOfCharacter;
    }

    public int getTypeOfCharacter() {
        return typeOfCharacter;
    }

    public void setTypeOfCharacter(int typeOfCharacter) {
        this.typeOfCharacter = typeOfCharacter;
    }
}
