package com.classic.project.model.raidboss.history;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table
public class RaidBossHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String raidbossName;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date deathTimer;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date windowTimer;
    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date aliveTimer;
    @Column
    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date registrationDate;

    public RaidBossHistory(String raidbossName, Date deathTimer, Date windowTimer, Date aliveTimer) {
	this.raidbossName = raidbossName;
	this.deathTimer = deathTimer;
	this.windowTimer = windowTimer;
	this.aliveTimer = aliveTimer;
    }

    public RaidBossHistory() {
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
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

    @Override
    public String toString() {
	return "RaidBossHistory{" +
	    "id=" + id +
	    ", raidbossName='" + raidbossName + '\'' +
	    ", deathTimer=" + deathTimer +
	    ", windowTimer=" + windowTimer +
	    ", aliveTimer=" + aliveTimer +
	    ", registrationDate=" + registrationDate +
	    '}';
    }
}
