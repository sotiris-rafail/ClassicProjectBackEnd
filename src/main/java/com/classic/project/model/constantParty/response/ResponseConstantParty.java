package com.classic.project.model.constantParty.response;

import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.constantParty.ConstantParty;
import com.classic.project.model.constantParty.CpNotFoundException;
import com.classic.project.model.user.User;
import com.classic.project.model.user.response.ResponseUser;

import java.util.List;
import java.util.Optional;

public class ResponseConstantParty {

    private String cpName;
    private int numberOfActives;
    private int numberOfBoxes;
    private int orfenPoints;
    private int corePoints;
    private int aqPoints;
    private List<ResponseUser> members;

    public ResponseConstantParty(String cpName, int numberOfActives, int numberOfBoxes, int orfenPoints, int corePoints, int aqPoints, List<ResponseUser> members) {
        this.cpName = cpName;
        this.numberOfActives = numberOfActives;
        this.numberOfBoxes = numberOfBoxes;
        this.orfenPoints = orfenPoints;
        this.corePoints = corePoints;
        this.aqPoints = aqPoints;
        this.members = members;
    }

    public ResponseConstantParty(String cpName) {
        this.cpName = cpName;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public int getNumberOfActives() {
        return numberOfActives;
    }

    public void setNumberOfActives(int numberOfActives) {
        this.numberOfActives = numberOfActives;
    }

    public int getNumberOfBoxes() {
        return numberOfBoxes;
    }

    public void setNumberOfBoxes(int numberOfBoxes) {
        this.numberOfBoxes = numberOfBoxes;
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

    public List<ResponseUser> getMembers() {
        return members;
    }

    public void setMembers(List<ResponseUser> members) {
        this.members = members;
    }

    public static ResponseConstantParty convertForLeader(Optional<ConstantParty> cpFromDB) {
        ConstantParty cp;
        if(!cpFromDB.isPresent()) {
            throw new CpNotFoundException();
        }
        cp = cpFromDB.get();
        return new ResponseConstantParty(cp.getCpName(), cp.getNumberOfActivePlayers(), cp.getNumberOfBoxes(), cp.getOrfenPoints(), cp.getCorePoints(), cp.getAqPoints(), ResponseUser.convertForCp(cp.getMembers()));
    }

    public static ResponseConstantParty convertForUser(ConstantParty cp) {
        return new ResponseConstantParty(cp == null ? "" : cp.getCpName());
    }
}