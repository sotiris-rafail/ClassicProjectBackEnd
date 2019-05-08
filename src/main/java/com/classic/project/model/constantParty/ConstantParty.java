package com.classic.project.model.constantParty;

import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class ConstantParty {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int cpId;
    @Column
    private int numberOfActivePlayers;
    @Column
    private int numberOfBoxes;
    @Column
    private int orfenPoints;
    @Column
    private int corePoints;
    @Column
    private int aqPoints;
    @Column
    private String cpName;
    @OneToMany(mappedBy = "cp", cascade = CascadeType.ALL)
    private List<User> members = new ArrayList<>();
    @OneToMany(mappedBy = "cpImg", cascade = CascadeType.ALL)
    private List<CpFile> images = new ArrayList<>();

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

    @JsonIgnore
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public int getOrfenPoints() {
        return orfenPoints;
    }

    public void setOrfenPoints(int orfenPoints) {
        this.orfenPoints = orfenPoints;
    }

    public int getCorePoints() {
        return corePoints;
    }

    public void setCorePoints(int corePoints) {
        this.corePoints = corePoints;
    }

    public int getAqPoints() {
        return aqPoints;
    }

    public void setAqPoints(int aqPoints) {
        this.aqPoints = aqPoints;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public List<CpFile> getImages() {
        return images;
    }

    public void setImages(List<CpFile> images) {
        this.images = images;
    }
}
