package com.classic.project.model.radiboss.response;

import com.classic.project.model.radiboss.RaidBoss;

import java.util.Date;

public class ResponseRaidBoss {

    private int raidBossId;
    private String name;
    private int level;
    private Date windowStarts;
    private Date windowEnds;
    private String whereItLives;
    private boolean isALive;
    private Date windowStartsJP;
    private Date windowEndsJP;
    public ResponseRaidBoss(int raidBossId, String name, int level, Date windowStarts, Date windowEnds, String whereItLives, boolean isALive) {
        this.raidBossId = raidBossId;
        this.name = name;
        this.level = level;
        this.windowStarts = windowStarts;
        this.windowEnds = windowEnds;
        this.whereItLives = whereItLives;
        this.isALive = isALive;
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

    public boolean isALive() {
        return isALive;
    }

    public void setALive(boolean ALive) {
        isALive = ALive;
    }

    public Date getWindowStartsJP() {
        return windowStartsJP;
    }

    public void setWindowStartsJP(Date windowStartsJP) {
        this.windowStartsJP = windowStartsJP;
    }

    public Date getWindowEndsJP() {
        return windowEndsJP;
    }

    public void setWindowEndsJP(Date windowEndsJP) {
        this.windowEndsJP = windowEndsJP;
    }

    public static ResponseRaidBoss convertForRaidBossTable(RaidBoss raidboss, Date windowStarts, Date windowEnds) {
        return new ResponseRaidBoss(raidboss.getRaidBossId(), raidboss.getName(), raidboss.getLevel(), windowStarts, windowEnds, raidboss.getWhereItLives(), isAlive(windowEnds));
    }

    private static boolean isAlive(Date windowEnds) {
        return windowEnds.before(new Date());
    }
}
