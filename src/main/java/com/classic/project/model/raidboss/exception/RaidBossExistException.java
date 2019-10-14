package com.classic.project.model.radiboss.exception;

public class RaidBossExistException extends  RuntimeException{
    public RaidBossExistException(String name){
        super("Raidboss " + name +" already exists");
    }
}
