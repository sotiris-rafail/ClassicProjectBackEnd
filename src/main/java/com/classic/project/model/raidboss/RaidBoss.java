package com.classic.project.model.raidboss;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class RaidBoss {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private int raidBossId;
    @Column
    private String name;
    @Column
    private String nameLowerCase;
    @Column
    private Date timeOfDeath;
    @Column
    private int level;
    @Column
    private String whereItLives;
    @Column
    private TypeOfRaidBoss typeOfRaidBoss;
    @Column
    private int epicBossPoints;
    @Column
    private String windowStarts;
    @Column
    private String windowEnds;
    @Column
    private boolean isUnknown;
    @Column
    private boolean isNotified;
    @Column
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registrationDate;

    public int getRaidBossId() {
        return raidBossId;
    }

    public void setRaidBossId(int raidBossId) {
        this.raidBossId = raidBossId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLowerCase() {
        return nameLowerCase;
    }

    public void setNameLowerCase(String nameLowerCase) {
        this.nameLowerCase = nameLowerCase;
    }

    public Date getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setTimeOfDeath(Date timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWhereItLives() {
        return whereItLives;
    }

    public void setWhereItLives(String whereItLives) {
        this.whereItLives = whereItLives;
    }

    public TypeOfRaidBoss getTypeOfRaidBoss() {
        return typeOfRaidBoss;
    }

    public void setTypeOfRaidBoss(TypeOfRaidBoss typeOfRaidBoss) {
        this.typeOfRaidBoss = typeOfRaidBoss;
    }

    public int getEpicBossPoints() {
        return epicBossPoints;
    }

    public void setEpicBossPoints(int epicBossPoints) {
        this.epicBossPoints = epicBossPoints;
    }

    public String getWindowStarts() {
        return windowStarts;
    }

    public void setWindowStarts(String windowStarts) {
        this.windowStarts = windowStarts;
    }

    public String getWindowEnds() {
        return windowEnds;
    }

    public void setWindowEnds(String windowEnds) {
        this.windowEnds = windowEnds;
    }

    public boolean isUnknown() {
        return isUnknown;
    }

    public void setUnknown(boolean unknown) {
        isUnknown = unknown;
    }

    public boolean isNotified() {
	return isNotified;
    }

    public void setNotified(boolean notified) {
	isNotified = notified;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    @Override
    public String toString() {
	return "RaidBoss{" +
	    "raidBossId=" + raidBossId +
	    ", name='" + name + '\'' +
	    ", timeOfDeath=" + timeOfDeath +
	    ", level=" + level +
	    ", whereItLives='" + whereItLives + '\'' +
	    ", typeOfRaidBoss=" + typeOfRaidBoss +
	    ", epicBossPoints=" + epicBossPoints +
	    ", windowStarts='" + windowStarts + '\'' +
	    ", windowEnds='" + windowEnds + '\'' +
	    ", isUnknown=" + isUnknown +
	    ", isNotified=" + isNotified +
	    '}';
    }
}
