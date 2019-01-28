package com.classic.project.model.radiboss;

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
    private Date timeOfDeath;
    @Column
    private Date timeOfReSpawn;
    @Column
    private int level;
    @Column
    private String whereItLives;
    @Column
    private TypeOfRaidBoss typeOfRaidBoss;
    @Column
    private int epicBossPoints;
    @Column
    private int windowStarts;
    @Column
    private int windowEnds;

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

    public Date getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setTimeOfDeath(Date timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }

    public Date getTimeOfReSpawn() {
        return timeOfReSpawn;
    }

    public void setTimeOfReSpawn(Date timeOfReSpawn) {
        this.timeOfReSpawn = timeOfReSpawn;
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

    public int getWindowStarts() {
        return windowStarts;
    }

    public void setWindowStarts(int windowStarts) {
        this.windowStarts = windowStarts;
    }

    public int getWindowEnds() {
        return windowEnds;
    }

    public void setWindowEnds(int windowEnds) {
        this.windowEnds = windowEnds;
    }
}
