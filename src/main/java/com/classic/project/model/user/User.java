package com.classic.project.model.user;

import com.classic.project.model.character.Character;
import com.classic.project.model.constantParty.ConstantParty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue
    @Column
    private int userId;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private TypeOfUser typeOfUser;
    @OneToMany(mappedBy = "characterId", cascade = CascadeType.ALL)
    private List<Character> characters = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "cpId")
    private ConstantParty cp;

   public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TypeOfUser getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(TypeOfUser typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public ConstantParty getCp() {
        return cp;
    }

    public void setCp(ConstantParty cp) {
        this.cp = cp;
    }
}
