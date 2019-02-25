package com.classic.project.model.radiboss.response;

import com.classic.project.model.radiboss.RaidBoss;
import com.classic.project.model.radiboss.RaidBossState;

import java.util.Date;

public class ResponseRaidBoss {

    private int raidBossId;
    private String name;
    private int level;
    private Date windowStarts;
    private Date windowEnds;
    private String whereItLives;
    private String raidBossState;
    public ResponseRaidBoss(int raidBossId, String name, int level, Date windowStarts, Date windowEnds, String whereItLives, String raidBossState) {
        this.raidBossId = raidBossId;
        this.name = name;
        this.level = level;
        this.windowStarts = windowStarts;
        this.windowEnds = windowEnds;
        this.whereItLives = whereItLives;
        this.raidBossState = raidBossState;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getWindowStarts() {
        return windowStarts;
    }

    public void setWindowStarts(Date windowStarts) {
        this.windowStarts = windowStarts;
    }

    public Date getWindowEnds() {
        return windowEnds;
    }

    public void setWindowEnds(Date windowEnds) {
        this.windowEnds = windowEnds;
    }

    public String getWhereItLives() {
        return whereItLives;
    }

    public void setWhereItLives(String whereItLives) {
        this.whereItLives = whereItLives;
    }

    public String getRaidBossState() {
        return raidBossState;
    }

    public void setRaidBossState(String raidBossState) {
        this.raidBossState = raidBossState;
    }

    public static ResponseRaidBoss convertForRaidBossTable(RaidBoss raidboss, Date windowStarts, Date windowEnds) {
        return new ResponseRaidBoss(raidboss.getRaidBossId(), raidboss.getName(), raidboss.getLevel(), windowStarts, windowEnds, raidboss.getWhereItLives(), raidBossState(windowStarts, windowEnds));
    }

    private static String raidBossState(Date windowStarts, Date windowEnds) {
        if(windowStarts.after(new Date())) {
            if(windowEnds.before(new Date())){
                return RaidBossState.ONWINDOW.name();
            } else {
                return RaidBossState.DEAD.name();
            }
        } else {
            return RaidBossState.ALIVE.name();
        }

    }
}
