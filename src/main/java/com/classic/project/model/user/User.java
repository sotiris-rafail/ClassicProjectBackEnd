package com.classic.project.model.user;

import com.classic.project.model.character.Character;
import com.classic.project.model.constantParty.ConstantParty;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int userId;
    @Column
    @Email
    private String email;
    @Column
    @Email
    private String emailLowerCase;
    @Column
    private String password;
    @Column
    private TypeOfUser typeOfUser;
    @Column
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registrationDate;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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

    public String getEmailLowerCase() {
        return emailLowerCase;
    }

    public void setEmailLowerCase(String emailLowerCase) {
        this.emailLowerCase = emailLowerCase;
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

    public Date getRegistrationDate() {
        return registrationDate;
    }
}
