package com.classic.project.model.raidboss.exception;

public class RaidBossExistException extends  RuntimeException{
    public RaidBossExistException(String name){
        super("Raidboss " + name +" already exists");
    }
}
