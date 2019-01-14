package com.classic.project.model.character;


import com.classic.project.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "Char")
public class Character {

    @Id
    @GeneratedValue
    @Column(name = "characterId")
    private int characterId;
    @Column
    private String inGameName;
    @Column
    private String inGameClass;
    @Column
    private int level;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

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

    public String getInGameClass() {
        return inGameClass;
    }

    public void setInGameClass(String inGameClass) {
        this.inGameClass = inGameClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
