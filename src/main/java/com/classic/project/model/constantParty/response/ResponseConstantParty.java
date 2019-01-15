package com.classic.project.model.constantParty.response;

import com.classic.project.model.constantParty.ConstantParty;

public class ResponseConstantParty {

    private String cpName;
    private int numberOfActives;
    private int numberOfBoxes;
    private int orfenPoints;
    private int corePoints;
    private int aqPoints;

    public ResponseConstantParty(String cpName, int numberOfActives, int numberOfBoxes, int orfenPoints, int corePoints, int aqPoints) {
        this.cpName = cpName;
        this.numberOfActives = numberOfActives;
        this.numberOfBoxes = numberOfBoxes;
        this.orfenPoints = orfenPoints;
        this.corePoints = corePoints;
        this.aqPoints = aqPoints;
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

    public static ResponseConstantParty convert(ConstantParty cp) {
        return new ResponseConstantParty(cp.getCpName(), cp.getNumberOfActivePlayers(), cp.getNumberOfBoxes(), cp.getOrfenPoints(), cp.getCorePoints(), cp.getAqPoints());
    }
}
