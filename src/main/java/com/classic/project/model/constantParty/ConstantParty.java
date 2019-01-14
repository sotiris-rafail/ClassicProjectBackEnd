package com.classic.project.model.constantParty;

import com.classic.project.model.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class ConstantParty {

    @Id
    @GeneratedValue
    @Column
    private int cpId;
    @Column
    private int numberOfActivePlayers;
    @Column
    private int numberOfBoxes;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<User> members = new ArrayList<>();

    public int getCpId() {
        return cpId;
    }

    public void setCpId(int cpId) {
        this.cpId = cpId;
    }

    public int getNumberOfActivePlayers() {
        return numberOfActivePlayers;
    }

    public void setNumberOfActivePlayers(int numberOfActivePlayers) {
        this.numberOfActivePlayers = numberOfActivePlayers;
    }

    public int getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(int numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
