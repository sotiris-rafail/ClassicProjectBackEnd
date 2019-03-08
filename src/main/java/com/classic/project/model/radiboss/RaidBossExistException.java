package com.classic.project.model.radiboss;

public class RaidBossExistException extends  RuntimeException{
    public RaidBossExistException(String name){
        super("Raidboss " + name +" already exists");
    }
}
