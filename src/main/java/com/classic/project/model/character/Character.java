package com.classic.project.model.character;


import com.classic.project.model.clan.Clan;
import com.classic.project.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Character {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int characterId;
    @Column
    private String inGameName;
    @Column
    private String inGameNameLowerCase;
    @Column
    private int level;
    @Column
    private ClassOfCharacter classOfCharacter;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clanId")
    private Clan clan;
    @Column
    private TypeOfCharacter typeOfCharacter;
    @Column
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registrationDate;

    public Character(String inGameName, int level, ClassOfCharacter classOfCharacter,
	TypeOfCharacter typeOfCharacter) {
	this.inGameName = inGameName;
	this.level = level;
	this.classOfCharacter = classOfCharacter;
	this.typeOfCharacter = typeOfCharacter;
    }

    public Character() {}

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public String getInGameName() {
        return inGameName;
    }

    public void setInGameName(String inGameName) {
        this.inGameName = inGameName;
    }

    public String getInGameNameLowerCase() {
        return inGameNameLowerCase;
    }

    public void setInGameNameLowerCase(String inGameNameLowerCase) {
        this.inGameNameLowerCase = inGameNameLowerCase;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public ClassOfCharacter getClassOfCharacter() {
        return classOfCharacter;
    }

    public void setClassOfCharacter(ClassOfCharacter classOfCharacter) {
        this.classOfCharacter = classOfCharacter;
    }

    public TypeOfCharacter getTypeOfCharacter() {
	return typeOfCharacter;
    }

    public void setTypeOfCharacter(TypeOfCharacter typeOfCharacter) {
	this.typeOfCharacter = typeOfCharacter;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
