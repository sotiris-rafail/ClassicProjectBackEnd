package com.classic.project.model.raidboss.history.response;

import com.classic.project.model.raidboss.history.RaidBossHistory;

import java.util.Date;

public class RaidBossHistoryResponse {

    private String raidbossName;
    private Date deathTimer;
    private Date windowTimer;
    private Date aliveTimer;

    public RaidBossHistoryResponse(String raidbossName, Date deathTimer, Date windowTimer, Date aliveTimer) {
	this.raidbossName = raidbossName;
	this.deathTimer = deathTimer;
	this.windowTimer = windowTimer;
	this.aliveTimer = aliveTimer;
    }

    public String getRaidbossName() {
	return raidbossName;
    }

    public void setRaidbossName(String raidbossName) {
	this.raidbossName = raidbossName;
    }

    public Date getDeathTimer() {
	return deathTimer;
    }

    public void setDeathTimer(Date deathTimer) {
	this.deathTimer = deathTimer;
    }

    public Date getWindowTimer() {
	return windowTimer;
    }

    public void setWindowTimer(Date windowTimer) {
	this.windowTimer = windowTimer;
    }

    public Date getAliveTimer() {
	return aliveTimer;
    }

    public void setAliveTimer(Date aliveTimer) {
	this.aliveTimer = aliveTimer;
    }

    public static RaidBossHistoryResponse raidBossHistoryResponse(RaidBossHistory raidBossHistory) {
        return new RaidBossHistoryResponse(raidBossHistory.getRaidbossName(), raidBossHistory.getDeathTimer(), raidBossHistory.getWindowTimer(), raidBossHistory.getAliveTimer());
    }
}
